

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
    		compile 'com.github.User:Repo:Tag'
    	}