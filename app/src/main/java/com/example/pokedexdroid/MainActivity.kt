package com.example.pokedexdroid

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedexdroid.data.RetrofitService.RetrofitFactoryService
import com.example.pokedexdroid.data.model.PokemonRemoteResult
import com.example.pokedexdroid.ui.theme.PokedexdroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexdroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PokemonList()
                }
            }
        }
    }
}

@Composable
fun PokemonList() {
    val pokemon = remember { mutableStateListOf<PokemonRemoteResult>() }

    LaunchedEffect(key1 = true) {
        val service = RetrofitFactoryService.createService()
        var pokemonList2: List<PokemonRemoteResult>? = null
        val pokemonList = service.getPokemonList()
        val pokemonFound = service.getPokemonByName("pikachu")
        pokemonList2 = pokemonList.results.map {
            service.getPokemonByName(it.name)
        }
        pokemon.addAll(pokemonList2)
    }


    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(pokemon.size) {
            PokemonCard(pokemon = pokemon[it])
        }
    }
}

@Composable
fun PokemonCard(pokemon: PokemonRemoteResult? = null, modifier: Modifier = Modifier) {
    var playCry by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_play_circle_filled_24),
                contentDescription = "reproduce its cries",
                modifier = modifier
                    .clickable { playCry = true }
                    .align(Alignment.End)
                    .padding(16.dp)
                    .size(44.dp),
                tint = Color.DarkGray,
            )

            AsyncImage(
                model = pokemon?.sprites?.other?.officialArtwork?.front_default,
                contentDescription = pokemon?.forms?.first()?.name,
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Text(
                text = pokemon?.forms?.first()?.name ?: "Unknown",
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )

            if (playCry) {
                PlayCriesSoundFromUrl(url = pokemon?.cries?.latest ?: "")
            }
            playCry = false
        }
    }
}

@Composable
fun PlayCriesSoundFromUrl(url: String) {
    val coroutineScope = rememberCoroutineScope()
    val mediaPlayer = remember { MediaPlayer() }

    LaunchedEffect(key1 = url) {
        withContext(Dispatchers.IO) {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
        }
        coroutineScope.launch {
            mediaPlayer.start()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            coroutineScope.launch {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexdroidTheme {
        //PokemonCard()
    }
}
