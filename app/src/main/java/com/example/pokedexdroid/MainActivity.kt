package com.example.pokedexdroid

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.pokedexdroid.data.model.PokemonCardData
import com.example.pokedexdroid.ui.theme.PokedexdroidTheme
import com.example.pokedexdroid.viewmodel.PokemonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle

class MainActivity : ComponentActivity() {

    // De esta forma podríamos instanciar al view model desde su factory:
    //val viewModel : PokemonViewModel by viewModels { PokemonViewModelFactory() }
    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexdroidTheme {
                var loading by remember { mutableStateOf(false) }
                val modifier = Modifier

                // A surface container using the 'background' color from the theme
                /*viewModel.loading.observe(this) {
                    loading = it
                }*/
                LaunchedEffect(true) {
                    viewModel.uiState.collect { state ->
                        loading = state.loading
                    }
                }

                if (loading) {
                    LoadingIndicator(modifier = modifier)
                } else {
                    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        PokemonList(viewModel, modifier, this)
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonList(viewModel: PokemonViewModel, modifier: Modifier, mainActivity: MainActivity) {
    val pokemon = remember { mutableStateListOf<PokemonCardData>() }

    LaunchedEffect(key1 = true) {
        this.launch {
            // LiveData observer implementation:
            /*viewModel.pokemonFirstPageList.observe(mainActivity) {
            pokemon.addAll(it)
        }*/
            viewModel.uiState.collect {
                mainActivity.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    pokemon.addAll(it.pokemonList)
                }
            }
        }
    }


    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(pokemon.size) {
            PokemonCard(pokemon = pokemon[it], modifier = modifier, context = mainActivity.applicationContext)
        }
    }
}

@Composable
fun PokemonCard(pokemon: PokemonCardData? = null, modifier: Modifier = Modifier, context: Context) {
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
                pokemon?.cries?.let {
                    PlayCriesSoundFromUrl(url = it.latest, playCry, context)
                    playCry = false
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .padding(22.dp)
                .size(222.dp), color = Color.White, strokeWidth = 16.dp
        )
    }
}

@Composable
fun PlayCriesSoundFromUrl(url: String, isPlaying: Boolean, context: Context) {
    val mediaPlayer = remember { MediaPlayer() }

    val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()

    LaunchedEffect(key1 = url) {
        withContext(Dispatchers.IO) {
            mediaPlayer.apply {
                setAudioAttributes(audioAttributes)
                setDataSource(context, Uri.parse(url))
                prepare()
            }

            if (isPlaying) {
                mediaPlayer.start()
            } else {
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
        LoadingIndicator(modifier = Modifier)
    }
}
