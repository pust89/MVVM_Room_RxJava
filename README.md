RxJava_Retrofit

<code>
  
 def room_version = "2.2.3"
 

  implementation "androidx.room:room-runtime:$room_version"
  
  
  
  annotationProcessor "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor
  
  
  // optional - RxJava support for Room
  
  
  implementation "androidx.room:room-rxjava2:$room_version"
  
  
  //RxJava2
  
  
  implementation "io.reactivex.rxjava2:rxjava:2.2.10"
  
  
  implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
  
  
  
  
  //Lifecycle
  
    def lifecycle_version = "2.1.0"
	
    // ViewModel and LiveData
	
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    
	
    
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$lifecycle_version" // For Kotlin use kapt instead of annotationProcessor


</code>




