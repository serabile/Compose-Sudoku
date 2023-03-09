package com.anafthdev.saku.ui.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anafthdev.saku.R
import com.anafthdev.saku.uicomponent.ScoreItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
	viewModel: ScoreViewModel,
	navController: NavController
) {

	if (viewModel.scores.isEmpty()) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.fillMaxSize()
		) {
			Text(
				text = stringResource(R.string.you_have_no_game_history),
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Medium
				)
			)
		}
	} else {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.systemBarsPadding()
		) {
			itemsIndexed(viewModel.scores) { i, score ->
				val dismissState = rememberDismissState(
					confirmValueChange = { dismissValue ->
						if (dismissValue == DismissValue.DismissedToEnd) {
							viewModel.deleteScore(score)
						}
						
						return@rememberDismissState false
					}
				)
				
				SwipeToDismiss(
					state = dismissState,
					directions = setOf(
						DismissDirection.StartToEnd
					),
					background = {
						Box(
							modifier = Modifier
								.fillMaxSize()
								.clip(MaterialTheme.shapes.large)
								.background(Color(0xFFFFA1A1))
								.align(Alignment.CenterVertically)
						) {
							Icon(
								painter = painterResource(id = R.drawable.ic_trash),
								contentDescription = null,
								modifier = Modifier
									.padding(horizontal = 16.dp)
									.align(Alignment.CenterStart)
							)
						}
						
						Icon(
							painter = painterResource(id = R.drawable.ic_trash),
							contentDescription = null,
							modifier = Modifier
								.padding(horizontal = 16.dp)
								.align(Alignment.CenterVertically)
						)
					},
					dismissContent = {
						ScoreItem(
							index = i + 1,
							score = score,
							date = viewModel.formatDate(score.date)
						)
					},
					modifier = Modifier
						.padding(
							vertical = 4.dp,
							horizontal = 8.dp
						)
				)
			}
		}
	}
}
