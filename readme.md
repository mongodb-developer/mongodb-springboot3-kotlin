# Getting Started with Kotlin Multiplatform Mobile(KMM) with Flexible Sync

> This is an introductory article on how to build your first Kotlin Multiplatform Mobile
> using Atlas Device Sync.

## Introduction

Mobile development has evolved a lot in recent years and in this tutorial we are going discuss
Kotlin Multiplatform Mobile (KMM), one such platform which disrupted the development
communities by its approach and thoughts on how to build mobile apps.

Traditional mobile apps, either built with a native or hybrid approach, had their tradeoffs from
development time to performance. But with the Kotlin Multiplatform approach, we can have the best of
both worlds.

## What is Kotlin Multiplatform Mobile (KMM)

Kotlin Multiplatform is all about code sharing within apps for different environments (iOS,
Android). Some common use cases for shared code are getting data from the network, saving it into
the device, filtering or manipulating data, etc. This is different from other cross-development
frameworks as this enforces developers to share only business logic code rather than complete code
which often makes things complicated, especially when it comes to building different complex
custom UI for each platform.

## Setting up your environment

If you are an Android developer then you don't need to do much, the primary development of KMM apps
is done using Android Studio. The only additional step for you is to install the KMM plugin via IDE
plugin manager and one of the key benefits of this is it allows to you build and run the iOS app as
well from Android Studio.

To enable iOS building and running via Android Studio your system should have Xcode installed, which
is development IDE for iOS development.

To verify all dependencies are installed correctly we can use `kdoctor`, which can be installed
using
brew.

```shell 
brew install kdoctor
```

## Building Hello World!

With our setup complete, it is time to get our hands dirty quickly and build our first Hello World
application.

Creating a KMM application is very easy, open Android Studio and then select Kotlin
Multiplatform App from the New Project template and hit next.

![KMM Template](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/multiplatform_mobile_project_wizard_1_8a88adc42c.png)

On the next screen, add the basic application details like the name of the application, location
of the project etc.

![Enter Project Name](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_08_at_20_50_55_f2ffda28f8.png)

Finally, select the dependency manager for the iOS app which is recommended for `Regular
framework` and then hit finish.

![Select Dependency Manager](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/multiplatform_mobile_project_wizard_3_cbf2318af4.png)

Once gradle sync is complete, we can run both iOS and Android app using the run button from the
toolbar.

![iOS app run](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_08_at_21_04_35_01d50b1923.png)

![android app run](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_08_at_21_04_47_abe8543547.png)

That will start the Android emulator or iOS simulator where our app would run.

<img src="https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/first_multiplatform_project_on_android_1_e53c9f0ee4.png"  width=40%> <img 
src="https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/first_multiplatform_project_on_ios_1_cda32945d8.png"  width=40%> 

## Basic of the Kotlin Multiplatform

Now it's time to understand what is happening under the hood to grasp the basic concepts of KMM.

### Understanding Project structure

Any KMM project can be split into three logic folders ie `androidApp`, `iosApp` and
`shared` and each of these folders has a specific purpose.

![Project structure](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_09_at_06_11_29_53ad58ddc1.png)

Since KMM is all about sharing business/logic-related code all the shared code is written under
`shared` the folder. This code is then exposed as libs to `androidApp` and `iosApp` folders
allowing us to use shared logic by calling classes or functions and building a user interface on top
of it.

### Writing platform-specific code

There can be a few use cases where you like to use platform-specific APIs for writing business logic
like in the `Hello World!` app where we wanted to know the platform type and version. To handle such
use cases KMM has introduced the concept of `actual` and `expect`, which can be thought of as KMM's
way of `interface` or `Protocols`.

In this concept, we define `expect` for the functionality to be exposed, and then we write its
implementation `actual` for the different environments. Something like this

```Kotlin 

expect fun getPlatform(): String

```

```kotlin
actual fun getPlatform(): String = "Android ${android.os.Build.VERSION.SDK_INT}"
```

```kotlin
actual fun getPlatform(): String =
    UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
```

In the above example, you could notice that we are using platform-specific APIs like `android.os` or
`UIDevice` in `shared` folder. To keep this organised and readable, KMM has divided the `shared`
folder into three subfolders - `commonMain`, `androidMain`, `iOSMain`.

![shared folder split](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_09_at_09_46_57_237bd899da.png)

With this, we covered the basics of KMM (and that's small learning curve for KMM is especially
for people coming from `android` background) needed before building a complex and full-fledged
real app.

## Building a more complex app

Now let's build our first real-world application, Querize, an app that helps you collect queries in
real-time during a session. Although this is a very simple app, it still covers all the basic
use cases highlighting the benefits of the KMM App with a complex one like accessing data in
real-time.

The Tech stack for our app would be

1. [JetPack Compose](https://developer.android.com/jetpack/compose) for UI building.
2. [Kotlin Multiplatform](https://kotlinlang.org/lp/mobile/) with Realm as a middle layer.
3. [Atlas Flexible Device Sync](https://www.mongodb.com/docs/atlas/app-services/sync/) from MongoDB,
   serverless backend supporting our data sharing.
4. [MongoDB Atlas](https://www.mongodb.com/atlas/database), our cloud database.

<img src="https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_20230109_134623_6ea6a87eec.png"  width=50%>

We would be following top to bottom approach in building the app, so let's start building the UI
using Jetpack compose with `ViewModel`.

```kotlin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Container()
            }
        }
    }
}


@Preview
@Composable
fun Container() {
    val viewModel = viewModel<MainViewModel>()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Querize",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_menu_24),
                        contentDescription = ""
                    )
                }
            )
        },
        containerColor = (Color(0xffF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_realm_logo),
                    contentScale = ContentScale.Fit,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .defaultMinSize(minHeight = 200.dp)
                        .padding(bottom = 20.dp),
                )
            }

            AddQuery(viewModel)

            Text(
                "Queries",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            QueriesList(viewModel)
        }
    }
}


@Composable
fun AddQuery(viewModel: MainViewModel) {

    val queryText = remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = "Enter your query here") },
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_baseline_send_24),
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.saveQuery(queryText.value)
                    queryText.value = ""
                })
        },
        value = queryText.value,
        onValueChange = {
            queryText.value = it
        })
}

@Composable
fun QueriesList(viewModel: MainViewModel) {

    val queries = viewModel.queries.observeAsState(initial = emptyList()).value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(8.dp),
        content = {
            items(items = queries, itemContent = { item: String ->
                QueryItem(query = item)
            })
        })
}

@Preview
@Composable
fun QueryPreview() {
    QueryItem(query = "Sample text")
}

@Composable
fun QueryItem(query: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(text = query, modifier = Modifier.fillMaxWidth())
    }
}


```

```kotlin
class MainViewModel : ViewModel() {

    private val repo = RealmRepo()
    val queries: LiveData<List<String>> = liveData {
        emitSource(repo.getAllData().flowOn(Dispatchers.IO).asLiveData(Dispatchers.Main))
    }

    fun saveQuery(query: String) {
        viewModelScope.launch {
            repo.saveInfo(query)
        }
    }
}
```

In our viewModel, we have a method `saveQuery` to capture the user queries and share them with the
speaker. This information is then passed on to our logic layer, `RealmRepo`, which is built using
Kotlin Multiplatform for Mobile (KMM) as we would like to reuse this for code when building an iOS
app.

```kotlin
class RealmRepo {

    suspend fun saveInfo(query: String) {

    }
}
```

Now to save and share this information we need to integrate this with Atlas Device Sync which
will automatically save and share this information with our clients in real time. To connect
with Device Sync, we need to add `Realm` SDK first to our project, which provides us
integration with Device Sync out of the box.

Realm is not just SDK for integration with Atlas Device Sync, but it's a very powerful
object-oriented mobile database built using KMM and one of the key advantages of using, this makes
our app work offline without any effort.

### Adding Realm SDK

#### Adding Realm plugin

Open `build.gradle` file under project root and add the `Realm` plugin

From

```kotlin
plugins {
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.20").apply(false)
}
```

To

```kotlin
plugins {
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.20").apply(false)
    // Added Realm plugin 
    id("io.realm.kotlin") version "0.10.0"
}
```

#### Enabling Realm Plugin

Now let us enable the Realm plugin for our project, therefore we would be making corresponding
changes to `build.gradle` file under `shared` module.

From

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}
```

To

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    // Enabled Realm Plugin
    id("io.realm.kotlin")
}
```

#### Adding dependencies

With the last step done, we are just one step away from completing the Realm setup. In this step, we
add the Realm dependency to our project.

Since the `Realm` database will be shared across all platforms, we will be adding the Realm
dependency to the common source `shared`. In the same `build.gradle` file, locate the `sourceSet`
tag and update it to:

From

   ```kotlin
 sourceSets {
    val commonMain by getting {
        dependencies {

        }
    }
    // Other config
}
   ```

To

   ```kotlin
 sourceSets {
   val commonMain by getting {
      dependencies {
         implementation("io.realm.kotlin:library-sync:1.4.0")
      }
   }
}
   ```

With this, we have completed the `Realm` setup for our KMM project. If you would like to use any
part of the SDK inside the Android module you can add the dependency in Android
Module `build.gradle` file

   ```kotlin
dependencies {
    compileOnly("io.realm.kotlin:library-sync:1.4.0")
}
   ```

Since Realm is an object-oriented database, therefore we can save objects directly without getting
into the hassle of converting them into different formats. To save any object into the `Realm`
database it should be derived from `RealmObject` class.

```kotlin
class QueryInfo : RealmObject {

    @PrimaryKey
    var _id: String = ""
    var queries: String = ""
}
```

Now let's save our query into the local database which will then be synced using Atlas Device Sync
and
saved into our cloud database, Atlas.

```kotlin
class RealmRepo {

    suspend fun saveInfo(query: String) {
        val info = QueryInfo().apply {
            _id = RandomUUID().randomId
            queries = query
        }
        realm.write {
            copyToRealm(info)
        }
    }
}
```

The next step is to create a `Realm` instance which we used to save the information. To create
a `Realm`, an instance of `Configuration` is needed which in turn needs a list of classes that can
be saved into the database.

```kotlin

val realm by lazy {
    val config = RealmConfiguration.create(setOf(QueryInfo::class))
    Realm.open(config)
}

```

This `Realm` instance is sufficient for saving data into the device but in our case we need to
integrate this with Atlas Device Sync to save and share our data into the cloud. To do this we need
four more steps:

1. Create a [free MongoDB account](https://account.mongodb.com/account/login).
2. Follow the setup wizard after signing up
   to [create a free cluster](https://www.mongodb.com/docs/atlas/tutorial/deploy-free-tier-cluster/).
3. Create an
   [App with App Service UI](https://www.mongodb.com/docs/atlas/app-services/manage-apps/create/create-with-ui/)
   to enable Atlas Device Sync.
4. Enable Atlas Device Sync using Flexible Sync, select the App services tab and enable sync as
   shown below
   ![Device Sync](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Enable_Sync_ebd2f2ae37.gif)

Now let's connect our Realm and Atlas Device Sync. To do this, we need to modify our `Realm`
instance creation. Instead of using `RealmConfiguration` we need to use `SyncConfiguration`.

`SyncConfiguration` instance can be created using its builder which needs a user instance and
`initialSubscriptions` as additional information. Since our application doesn't have user
registration form we can use anonymous sign-in provided by Atlas App service to identify as user
session. So our updated code looks like this

```kotlin

private val appServiceInstance by lazy {
    val configuration =
        AppConfiguration.Builder("application-0-elgah").log(LogLevel.ALL).build()
    App.create(configuration)
}
```

```kotlin
lateinit var realm: Realm

private suspend fun setupRealmSync() {
    val user = appServiceInstance.login(Credentials.anonymous())
    val config = SyncConfiguration
        .Builder(user, setOf(QueryInfo::class))
        .initialSubscriptions { realm ->
            // information about the data that can be read or modified. 
            add(
                query = realm.query<QueryInfo>(),
                name = "subscription name",
                updateExisting = true
            )
        }
        .build()
    realm = Realm.open(config)
}
```

```kotlin
suspend fun saveInfo(query: String) {
    if (!this::realm.isInitialized) {
        setupRealmSync()
    }

    val info = QueryInfo().apply {
        _id = RandomUUID().randomId
        queries = query
    }
    realm.write {
        copyToRealm(info)
    }
}
```

Now the last step to complete our application is to write a read function to get all the queries
and show it on UI.

```kotlin
suspend fun getAllData(): CommonFlow<List<String>> {
    if (!this::realm.isInitialized) {
        setupRealmSync()
    }
    return realm.query<QueryInfo>().asFlow().map {
        it.list.map { it.queries }
    }.asCommonFlow()
}
```

With this done our application is ready to send and receive data in real-time. Yes, in real-time. No
additional implementation is required to make it real-time.

Also, you can view or modify the data received via the `saveInfo` function using the `Atlas` UI.

![Atlas UI](https://mongodb-devhub-cms.s3.us-west-1.amazonaws.com/Screenshot_2023_01_10_at_09_17_27_2a1a38dece.png)

## Summary

Thank you for reading and hopefully find this article informative! The Complete source code of
the app can be found on [GitHub](https://github.com/mongodb-developer/Conference-Queries-App).

If you have any queries or comments, you can share them on
the [MongoDB Realm forum](https://www.mongodb.com/community/forums/c/realm/9) or tweet
me [@codeWithMohit](http://twitter.com/codeWithMohit).



