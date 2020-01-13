package com.pustovit.contactmanager;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pustovit.contactmanager.db.AppDatabase;
import com.pustovit.contactmanager.db.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Pustovit Vladimir on 13.01.2020.
 * vovapust1989@gmail.com
 */

public class ContactRepository {

    private Application application;
    private AppDatabase database;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<List<Contact>> contactsLiveData;

    private long rowIdOfTheItemInserted;

    public ContactRepository(Application application) {
        this.application = application;
        this.database = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, AppDatabase.DATABASE_NAME).build();

        compositeDisposable = new CompositeDisposable();
        Flowable<List<Contact>> flowable = database.getContactDAO().getContacts();

        contactsLiveData = new MutableLiveData<>();
        compositeDisposable.add(flowable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contact>>() {
                               @Override
                               public void accept(List<Contact> contacts) throws Exception {
                                   contactsLiveData.postValue(contacts);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));

    }

    public void createContact(final String name, final String email) {

        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                rowIdOfTheItemInserted = database.getContactDAO().addContact(new Contact(0, name, email));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "Contact added successfully " + rowIdOfTheItemInserted, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                    }
                }));

    }

    public void updateContact(final Contact contact, String name, String email) {


        contact.setName(name);
        contact.setEmail(email);

        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                database.getContactDAO().updateContact(contact);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "Contact updated successfully ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                    }
                }));

    }

    public void deleteContact(final Contact contact) {
        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                database.getContactDAO().deleteContact(contact);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "Contact deleted successfully ",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred",Toast.LENGTH_LONG).show();
                    }
                }));

    }

    public MutableLiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

    public void clear(){
        compositeDisposable.clear();
    }
}
