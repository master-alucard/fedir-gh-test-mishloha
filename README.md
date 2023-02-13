## Description

- This is a test Android project with 3 screens. I shows main code structure and code styling. The whole Android project is build on Java.

- Main screen consists of 2 tabs.
  First provides the ability to search github repositories by selected period of time with sorting by stars.
  Second shows favorite repositories.
  There is a third screen with github repository details. It appears when user clicks on item from list.
  1 and 2 screens have additional UI realization for landscape view (useful for tablets).
  Also user can mark any repository as favorite and see it on favorite repositories screen.

- For application architecture I used Presentation-Domain-Data. It helps to provide clean code, as well as recommendations and best practices by Google.
  Structure related to UI is contained in presentation(UI) layer (Activities, Fragments, ViewModels etc).
  Data, repositories, Api services are contained in Data layer.
  Domain layer is skipped in current project, but can be added between UI and Data. It will contain interactors and complex data business logic.
  Domain layer helps to avoid code duplication in big projects.

- Data layer consists of entities, repositories, different data sources. Also it contains data business logic if there is no Domain layer.

- Presentaion(UI) layer has ViewModel to persist UI state and access business logic.

- Also there is a DI layer for DI Hilt library. It is a modern Google library based on Dagger2.
  It is useful for maintaining big and average size projects. It can prevent potential memory leaks, crashes. Also it helps to produce clean code.

- For Api requests Retrofit library is used. It's a common API library with rich tools included.
  By KISS principle I keep Retrofit realization with base Call class. But in future it can be improved by moving to RxJava or Kotlin Flow.

- For loading images Glide library is used.
  For caching images I used build-in feature diskCacheStrategy(DiskCacheStrategy.ALL).
  For mapping UI elements into classes Data Binding is used. Which is a very useful feature. It also helps to keep the code clean and provides a better experience than findViewById.

- Also, as usual good practice, all strings, dimensions, colors are located in their appropriate resource files.
  This helps not to waste extra time on global changes in margins, test sizes, colors, view sizes etc.

- For pagination I used my custom realization of infinite pagination. It has advantages and disadvantages.
  The benefits are that it's simple and provides a clean mechanism for smooth pages loading. Which reduces the time it takes to load the next page and improves user experience.
  The main disadvantage is that all data is kept in RAM. But this can be improved or moved to Paging3 realization.

- App contains simple info and error messages.
  If there is no internet connection or poor connection it will show the message "No internet connection".
  If the user makes too many requests to Github, a message will also be displayed.
  Also there are info messages for the number of results after the search and on making favorite action.

#### Possible TODO:
- migrate to Kotlin :)
- add day and night UI styling,
- move to RxJava or Kotlin Flow,
- move to Paging3,
- add a Domain layer if needed,
- add savedStateHandle and additional class for UIEvents to ViewModels,
- replace info and error Toasts by Popup dialogs or Snackbar with undo button (for example in case of removal from favorites)