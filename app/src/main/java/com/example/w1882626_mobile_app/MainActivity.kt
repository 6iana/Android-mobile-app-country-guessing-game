package com.example.flag_game_cw


import android.os.Bundle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.font.FontWeight
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import kotlin.random.Random
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigate()
        }
    }
}

@Composable
fun Menu(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("guess_the_country") },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Text(
                text = "Guess The Country",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("guess_hints") },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Text(
                text = "Guess-Hints",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("guess_the_flag") },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Text(
                text = "Guess The Flag",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("advanced_level") },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Text(
                text = "Advanced Level",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Navigate() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            Menu(navController)
        }
        composable("guess_the_country") {
            GuessTheCountry(navController)
        }
        composable("guess_hints") {
            GuessHints(navController)
        }
        composable("guess_the_flag") {
            GuessTheFlag(navController)
        }
        composable("advanced_level") {
            AdvancedLevel(navController)
        }
    }
}

val countryNames = listOf(
    "Wales" to R.drawable.wls,
    "United Kingdom" to R.drawable.gb,
    "Canada" to R.drawable.ca,
    "China" to R.drawable.cn,
    "Germany" to R.drawable.de,
    "Spain" to R.drawable.es,
    "United States" to R.drawable.us,
    "Japan" to R.drawable.jp,
    "Georgia" to R.drawable.ge,
    "Romania" to R.drawable.ro,
    "Italy" to R.drawable.it,
    "Russia" to R.drawable.ru,
    "Australia" to R.drawable.au,
    "Netherlands" to R.drawable.nl,
    "Switzerland" to R.drawable.ch,
    "Portugal" to R.drawable.pt,
    "Mexico" to R.drawable.mx,
    "India" to R.drawable.`in`,
    "Brazil" to R.drawable.br,
    "Saudi Arabia" to R.drawable.sa,
    "Thailand" to R.drawable.th,
    "Nigeria" to R.drawable.ng,
    "Greece" to R.drawable.gr,
    "Ireland" to R.drawable.ie,
    "South Africa" to R.drawable.za,
    "Czech Republic" to R.drawable.cz,
    "Scotland" to R.drawable.sct,
    "France" to R.drawable.fr,
    "Poland" to R.drawable.pl,
    "Morocco" to R.drawable.ma
)

@Composable
fun GuessTheCountry(navController: NavHostController) {
    var randomFlagIndex by remember { mutableStateOf(Random.nextInt(0, countryNames.size)) }
    var flagResourceId by remember { mutableStateOf(countryNames[randomFlagIndex].second) }
    var correctCountryName by remember { mutableStateOf(countryNames[randomFlagIndex].first) }
    var userAnswer by remember { mutableStateOf("") }
    var selectedCountryIndex by remember { mutableStateOf(-1) }
    var submitResult by remember { mutableStateOf<String?>(null) }
    var submitEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Row for buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Button(
                onClick = {
                    navController.popBackStack("menu", inclusive = false)
                }
            ) {
                Text("Back")
            }

            // Submit or Next button based on state
            Button(
                onClick = {
                    if (submitEnabled) {
                        if (selectedCountryIndex != -1) {
                            submitResult =
                                if (userAnswer.equals(correctCountryName, ignoreCase = true)) {
                                    "CORRECT"
                                } else {
                                    "WRONG"
                                }
                            submitEnabled = false
                        }
                    } else {
                        // Change to next flag
                        randomFlagIndex = Random.nextInt(0, countryNames.size)
                        correctCountryName = countryNames[randomFlagIndex].first
                        flagResourceId = countryNames[randomFlagIndex].second
                        submitResult = null
                        userAnswer = ""
                        selectedCountryIndex = -1
                        submitEnabled = true
                    }
                }
            ) {
                Text(if (submitEnabled) "Submit" else "Next")
            }
        }

        // Loads flag image
        LoadFlagImage(flagResourceId)

        // Displays the country buttons
        ListOfCountryButtons(countryNames.map { it.first }, selectedCountryIndex) { index ->
            selectedCountryIndex = index
            userAnswer = countryNames[index].first
        }

        // Displays results
        submitResult?.let { result ->
            val textColor =
                if (result == "CORRECT")
                    Color.Green
                else
                    Color.Red
            AlertDialog(
                onDismissRequest = { submitResult = null },
                title = {
                    Text(
                        text = result,
                        color = textColor,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { submitResult = null }
                    ) {
                        Text("OK")
                    }
                },
                // Displays the correct country name in blue if the answer is wrong
                text = {
                    if (result == "WRONG") {
                        Column {
                            Text(
                                text = "Correct country: $correctCountryName",
                                color = Color.Blue,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    } else {
                        Text("")
                    }
                }
            )
        }
    }
}

@Composable
fun LoadFlagImage(imageResource: Int) {
    // Column to center align the image
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Remember the image painter to optimize loading
        val painter = rememberImagePainter(
            data = imageResource,
            builder = {
                crossfade(true) // Enable cross-fade animation
            }
        )

        // Displays the image
        Image(
            painter = painter,
            contentDescription = "Flag",
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp),
        )
    }
}

@Composable
fun ListOfCountryButtons(countryNames: List<String>, selectedCountryIndex: Int, onCountrySelected: (Int) -> Unit) {
    // Column to arrange buttons vertically and with scrolling
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()), // Remembers the scroll state for scrolling functionality
        verticalArrangement = Arrangement.spacedBy(5.dp), // Spacing between buttons
    ) {
        // Iterate through country names to create all the country buttons
        countryNames.forEachIndexed { index, country ->
            val isSelected = index == selectedCountryIndex
            val buttonColor = if (isSelected) Color.Black else Color.Transparent // Color based on selection state
            // Button for each country
            Button(
                onClick = { onCountrySelected(index) }, // Invoke the callback when button is clicked
                modifier = Modifier
                    .background(buttonColor)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 100.dp)

            ) {
                Text(country) // Displays the country name on button
            }
        }
    }
}

@Composable
fun GuessHints(navController: NavHostController) {
    var userAns by remember { mutableStateOf("") }
    var submitResult by remember { mutableStateOf<String?>(null) }
    var randomFlagIndex by remember { mutableStateOf(Random.nextInt(0, countryNames.size)) }
    var correctCountryName by remember { mutableStateOf(countryNames[randomFlagIndex].first.lowercase()) }
    var dashes by remember { mutableStateOf(correctCountryName.replace(Regex("[a-zA-Z]"), "-")) }
    var flagId by remember { mutableStateOf(countryNames[randomFlagIndex].second) }
    var attemptsLeft by remember { mutableStateOf(3) }
    var gameOver by remember { mutableStateOf(false) } // Flag to track game over state

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                navController.popBackStack("menu", inclusive = false)
            }
        ) {
            Text("Back")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Loads and displays the flag image
        LoadFlagImage(flagId)

        // Text field for user input
        OutlinedTextField(
            value = userAns,
            onValueChange = { userAns = it },
            label = { Text("Enter a single letter to guess") },
            singleLine = true,
            enabled = !gameOver, // Disable text field if the game is over
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Displays dashes representing the amount of letters in the country's name
        Text(
            text = dashes,
            fontSize = 100.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Displays submission result or number of attempts left
        if (submitResult != null) {
            val textColor = if (submitResult == "CORRECT") Color.Green else Color.Red
            Text(
                text = submitResult!!,
                color = textColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Next button
            Button(
                onClick = {
                    // Resets values and generate new flag
                    userAns = ""
                    submitResult = null
                    randomFlagIndex = Random.nextInt(0, countryNames.size)
                    correctCountryName = countryNames[randomFlagIndex].first.lowercase()
                    dashes = correctCountryName.replace(Regex("[a-zA-Z]"), "-")
                    flagId = countryNames[randomFlagIndex].second
                    attemptsLeft = 3
                    gameOver = false // Resets game over state
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Next")
            }
        } else {
            // Submit button
            Button(
                onClick = {
                    if (userAns.length == 1) {
                        val char = userAns.lowercase()[0]
                        val updatedDash = StringBuilder(dashes)
                        var correctGuess = false
                        for (i in correctCountryName.indices) {
                            if (correctCountryName[i] == char) {
                                updatedDash[i] = char
                                correctGuess = true
                            }
                        }
                        if (!correctGuess) {
                            attemptsLeft--
                        }
                        dashes = updatedDash.toString()
                        if (dashes == correctCountryName || attemptsLeft == 0) {
                            submitResult = if (dashes == correctCountryName) "CORRECT" else "WRONG"
                            gameOver = true // Sets game over state if attempts are up or answer is correct
                        }
                        userAns = "" // Clears the input after pressing the submit button
                    }
                },
                modifier = Modifier.padding(top = 64.dp)
            ) {
                Text(if (submitResult == null) "Submit" else "Next", fontSize = 24.sp)
            }

            // Displays the number of attempts left
            if (attemptsLeft > 0) {
                Text(
                    text = "Attempts left: $attemptsLeft",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Displays the correct country name in blue if the game is over
        if (gameOver) {
            Text(
                text = "Correct country: ${countryNames[randomFlagIndex].first}",
                fontSize = 20.sp,
                color = Color.Blue,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun GuessTheFlag(navController: NavHostController) {
    var countryFlags by remember { mutableStateOf(randomFlags()) }
    var correctFlagIndex by remember { mutableStateOf(correctFlag(countryFlags).first) }
    var submitResult by remember { mutableStateOf<String?>(null) }
    var selectionEnabled by remember { mutableStateOf(true) } // Flag to enable/disable flag selection

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.popBackStack("menu", inclusive = false)
                }
            ) {
                Text("Back")
            }
        }

        // Displays the flag images
        countryFlags.forEachIndexed { index, (country, flagResourceId) ->
            ClickableFlag(
                flagResourceId = flagResourceId,
                onClick = {
                    if (selectionEnabled) {
                        selectionEnabled = false // Disable flag selection after the user's selection
                        if (index == correctFlagIndex) {
                            submitResult = "CORRECT!"
                        } else {
                            submitResult = "WRONG!"
                        }
                    }
                },
                isEnabled = selectionEnabled // Pass the flag to enable/disable flag selection
            )
        }

        // Displays the correct name for the flag
        Text(
            text = countryFlags[correctFlagIndex].first,
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        // Displays the result
        if (submitResult != null) {
            val textColor = if (submitResult == "CORRECT!") Color.Green else Color.Red
            Text(
                text = submitResult!!,
                color = textColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Next button
        Button(
            onClick = {
                countryFlags = randomFlags()
                correctFlagIndex = correctFlag(countryFlags).first
                submitResult = null
                selectionEnabled = true // Enable flag selection for the next round
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .height(60.dp)
        ) {
            Text(
                "Next",
                fontSize = 24.sp
            )
        }
    }
}

// Allows flag image to be clicked
@Composable
fun ClickableFlag(flagResourceId: Int, onClick: () -> Unit, isEnabled: Boolean) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Image(
            painter = rememberImagePainter(flagResourceId),
            contentDescription = null,
            modifier = Modifier.size(170.dp)
        )
    }
}

// Generates 3 random flags
fun randomFlags(): List<Pair<String, Int>> {

    val countryFlags = mutableListOf<Pair<String, Int>>()
    while (countryFlags.size < 3) {
        val randomFlagIndex = Random.nextInt(0, countryNames.size)
        if (!countryFlags.any { it.second == countryNames[randomFlagIndex].second }) {
            countryFlags.add(countryNames[randomFlagIndex])
        }
    }
    return countryFlags
}
// Function that selects one flag as the correct flag
fun correctFlag(countryFlags: List<Pair<String, Int>>): Pair<Int, String> {

    val correctFlagIndex = Random.nextInt(0, 3)
    val correctCountryName = countryFlags[correctFlagIndex].first
    return correctFlagIndex to correctCountryName
}

@Composable
fun AdvancedLevel(navController: NavHostController) {
    var countryFlags by remember { mutableStateOf(randomFlags(3)) }
    var Result by remember { mutableStateOf<String?>(null) }
    var userAnswers by remember { mutableStateOf(List(3) { "" }) }
    var finishedGuess by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(List(countryFlags.size) { false }) }
    var incorrectAttempts by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) } // Variable to keep track of user's score

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Display user's score on the top right of the screen
        Text(
            text = "Score: $score",
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Back button
            Button(
                onClick = {
                    navController.popBackStack("menu", inclusive = false)
                }
            ) {
                Text("Back")
            }

            // Flags and text fields
            for (i in countryFlags.indices) {
                val isCorrect = correctAnswers[i]

                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    LoadFlagImage(imageResource = countryFlags[i].second)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = userAnswers[i],
                        onValueChange = {
                            if (!finishedGuess || !correctAnswers[i]) {
                                userAnswers = userAnswers.toMutableList().apply { this[i] = it }
                            }
                        },
                        label = { Text("Enter country name") },
                        singleLine = true,
                        enabled = !finishedGuess || !correctAnswers[i], // Enable text field if guessing is not complete or the answer is incorrect
                        textStyle = TextStyle(
                            color = if (finishedGuess && correctAnswers[i]) Color.Green else if (finishedGuess && !correctAnswers[i]) Color.Red else Color.Black // Change color based on correct answer after submission
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Displays the correct answer below incorrect answer after 3 incorrect attempts
                    if (finishedGuess && incorrectAttempts >= 3 && !correctAnswers[i] && userAnswers[i].isNotBlank()) {
                        Text(
                            text = "Correct Answer: ${countryFlags[i].first}",
                            color = Color.Blue,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Display the results
            Result?.let { result ->
                val textColor = if (result == "CORRECT!") Color.Green else Color.Red
                Text(
                    text = result,
                    color = textColor,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Displays the next button after 3 incorrect attempts or if all 3 answers are correct
            if (finishedGuess && (incorrectAttempts >= 3 || correctAnswers.all { it })) {
                Button(
                    onClick = {
                        // Update score based on correct answers
                        score += correctAnswers.count { it }

                        // Reset values and generate new flag
                        userAnswers = List(3) { "" }
                        correctAnswers = List(3) { false }
                        finishedGuess = false
                        Result = null
                        countryFlags = randomFlags(3)
                        incorrectAttempts = 0
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Next")
                }
            } else if (!finishedGuess || incorrectAttempts < 3) {
                // Submit button
                Button(
                    onClick = {
                        if (userAnswers.all { it.isNotBlank() }) {
                            for (i in countryFlags.indices) {
                                correctAnswers = correctAnswers.toMutableList().apply {
                                    set(
                                        i,
                                        userAnswers[i].equals(
                                            countryFlags[i].first,
                                            ignoreCase = true
                                        )
                                    )
                                }
                            }
                            Result =
                                if (correctAnswers.all { it }) "CORRECT!" else "INCORRECT!"
                            finishedGuess =
                                true // Set isGuessingComplete to true when guessing is complete

                            if (!correctAnswers.all { it }) {
                                // Increment incorrect attempts
                                incorrectAttempts++
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

// Generates a list of random flags
fun randomFlags(count: Int): List<Pair<String, Int>> {
    val allCountries = countryNames.shuffled()
    return allCountries.subList(0, count)
}