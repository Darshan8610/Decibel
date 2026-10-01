package com.decibel.music.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.decibel.music.ui.theme.typo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.blog_promo_message
import com.decibel.music.composeapp.generated.resources.blog_promo_title
import com.decibel.music.composeapp.generated.resources.later
import com.decibel.music.composeapp.generated.resources.mono
import com.decibel.music.composeapp.generated.resources.visit_blog

@Composable
@ExperimentalMaterial3Api
fun BlogPromoDialog(
    onDismissRequest: () -> Unit,
    onVisitBlog: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        properties =
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
        onDismissRequest = {
            onDismissRequest.invoke()
        },
        confirmButton = {
            TextButton(onClick = {
                onVisitBlog.invoke()
                uriHandler.openUri("https://decibel.app")
            }) {
                Text(
                    stringResource(Res.string.visit_blog),
                    style = typo().bodySmall,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest.invoke()
            }) {
                Text(
                    stringResource(Res.string.later),
                    style = typo().bodySmall,
                )
            }
        },
        icon = {
            Icon(painterResource(Res.drawable.mono), "App Icon")
        },
        title = {
            Text(
                stringResource(Res.string.blog_promo_title),
                style = typo().labelSmall,
            )
        },
        text = {
            Text(
                stringResource(Res.string.blog_promo_message),
                textAlign = TextAlign.Center,
                style = typo().bodySmall,
            )
        },
    )
}
