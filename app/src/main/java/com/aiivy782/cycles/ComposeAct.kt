package com.aiivy782.cycles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar

fun ConstraintLayout(background: Modifier, function: Any) {
    TODO("Not yet implemented")
}

@Composable
@Preview(showBackground = true)
fun MainActivityScreen() {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.md_theme_light_background))
    ) {
        val (resetButton, toolbar, timerContainer) = createRefs()

        ExtendedFloatingActionButton(
            onClick = { /* Handle reset action */ },
            text = { Text(text = stringResource(id = R.string.action_reset)) },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.restart_alt_24),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .constrainAs(resetButton) {
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
        )

        MaterialToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = colorResource(id = R.color.primary_container)
                )
            },
            modifier = Modifier
                .constrainAs(toolbar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(timerContainer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(toolbar.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .width(300.dp)
        ) {
            val (timerText, progressBar) = createRefs()

            Text(
                text = stringResource(id = R.string.timerTextView, "00:00"),, // Replace with actual timer value
                fontFamily = FontFamily(Font(R.font.google_sans_regular)),
                fontSize = 36.sp,
                color = colorResource(id = R.color.on_background),
                modifier = Modifier
                    .constrainAs(timerText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(progressBar.top, margin = 8.dp)
                    }
            )

            LinearProgressIndicator(
                progress = 0f, // Replace with actual progress value
                modifier = Modifier
                    .constrainAs(progressBar) {
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(timerText.bottom, margin = 8.dp)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(4.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }
}