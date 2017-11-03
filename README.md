# HorizontalScrollBarView
scrollbar view for horizontal recyclerview

## Getting started

### Dependency

```
maven {
   url "https://jitpack.io"
}

dependencies {
    compile 'com.github.simoneventurini:HorizontalScrollBarView:0.2.0'
}
```

### Usage

### XML

```xml

         <com.serte.horizontalscrollbar.HorizontalScrollBarView
            android:id="@+id/horizontalScrollBarView"
            android:layout_width="match_parent"
            android:layout_height="4dp"
            android:layout_marginLeft="10dp"
            android:layout_marginRight="10dp"
            android:layout_marginTop="10dp"
            android:background="@drawable/horizontalscrollthumbtrack"
            app:animToScrollingDuration="200"
            app:animToStaticDuration="1500"
            app:enableAnim="true"
            app:roundDimen="2dp"
            app:scrollColor="@color/scrollBarColor"
            app:scrollingAlpha="1.0"
            app:staticAlpha="0.2" />
```

### JAVA
```java
        HorizontalScrollBarView horizontalScrollBarView = findViewById(R.id.horizontalScrollBarView);
        horizontalScrollBarView.setRecyclerView(recyclerView);

```