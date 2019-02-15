# HigherLower-Android-Game
A replica of higher or lower game using Google searches.
The data is based on global monthly searches in 2017.

To check the project in [Google Play](https://play.google.com/store/apps/details?id=com.htaka.higherlower/).


## Getting Started
Import the project in Android Studio using Github.
Enable The Anonymous sign in method to firebase. add the firebase-google.json, and the project is ready to run.

### Search Terms
The Term class contains title and average searches of the term. the game picks random two terms from the list that is imported through
`SetAllTrends` function.

### Testing Code
Example of testing questions and how to change them:

`private void SetAllTrends() {
  terms.add(new Terms("PewDiePie", 81000000000));
  terms.add(new Terms("T-Series", 81000000000));
}`

## Deployment
1) Change the Log in method to your needs in the firebase project console.
2) Import the pictures to firebase storage otherwise modify Term class to use links.
3) Change the package name.
4) Add the questions to SetAllTrends in the MainActivity class.

## Built With
1) Android Studio,
2) Gradle

## License
Anyone can write, change the code, use however in the liking, free for all.

## Acknowledgments
[Higher Lower Offical](http://www.higherlowergame.com/).
