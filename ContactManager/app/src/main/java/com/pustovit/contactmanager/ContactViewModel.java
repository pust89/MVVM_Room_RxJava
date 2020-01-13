package com.pustovit.contactmanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pustovit.contactmanager.db.entity.Contact;

import java.util.List;

/**
 * Created by Pustovit Vladimir on 13.01.2020.
 * vovapust1989@gmail.com
 */

public class ContactViewModel extends AndroidViewModel {
    ContactRepository repository;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        repository = new ContactRepository(application);
    }

    public LiveData<List<Contact>> getAllContacts(){
        return repository.getContactsLiveData();
    }

    public void createContact(String name, String email){
        repository.createContact(name, email);
    }

    public void updateContact(Contact contact, String name, String email){
        repository.updateContact(contact, name, email);
    }

    public void deleteContact(Contact contact){
        repository.deleteContact(contact);
    }

    public void clear(){
        repository.clear();
    }
}
