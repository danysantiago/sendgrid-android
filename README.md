# sendgrid-android

**Deprecated and unmaintained. Please use an alternative.**

This Android module allows you to quickly and easily send emails through SendGrid using Java in Android. It is basically a modified pull from [SendGrid's Java Library](https://github.com/sendgrid/sendgrid-java), slightly modified to use Android's Http Library.

```java
@Override
protected Void doInBackground(Void... params) {

  try {
    SendGrid sendgrid = new SendGrid(SENDGRID_USERNAME, SENDGRID_PASSWORD);

    SendGrid.Email email = new SendGrid.Email();

    // Get values from edit text to compose email
    // TODO: Validate edit texts
    email.addTo(mTo);
    email.setFrom(mFrom);
    email.setSubject(mSubject);
    email.setText(mText);

    // Attach image
    if (mUri != null) {
      email.addAttachment(mAttachmentName, mAppContext.getContentResolver().openInputStream(mUri));
    }

    // Send email, execute http request
    SendGrid.Response response = sendgrid.send(email);
    mMsgResponse = response.getMessage();

    Log.d("SendAppExample", mMsgResponse);

  } catch (SendGridException | IOException e) {
    Log.e("SendAppExample", e.toString());
  }

  return null;
}
```

# Setup

#### Gradle

`compile 'com.github.danysantiago:sendgrid-android:1'`

#### Maven
```
<dependency>
    <groupId>com.github.danysantiago</groupId>
    <artifactId>sendgrid-android</artifactId>
    <version>1</version>
</dependency>
```

#### Download

Download this repository and add the [Android Library Module](https://github.com/danysantiago/sendgrid-android/tree/master/library) to your project. Then add the dependency on your gradle file:
```groovy
...
dependencies {
	...
    compile project(':sendgrid-android')
}
```

### Additional Steps:

Because the Library uses an updated version of [Apache's Http Library](https://hc.apache.org/httpcomponents-client-4.3.x/android-port.html) we need to add the following packaging options so the APK gets built correctly, without libraries conflicting. This goes into your app's build.gradle.
```groovy
...
android {
	...
    packagingOptions {
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
    }
}
```

# Example App

This repository contains a full demo app to play with, currently supporting only image attachments.

![Screenshot](http://i.imgur.com/W5Zqic8.png)
