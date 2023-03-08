package com.lamda.projectnotes.presentation.home.components



import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    textFieldValue: TextFieldValue,
    text: String,
    onTextChange: (String) -> String,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit

    ) {
        //TODO() search textfield at the bottom of the categories chips
}




@Preview
@Composable
fun SearchTextFieldPreview(){

}

//            TextField(
//                value = text,
//                onValueChange = {onTextChange(it)},
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = Text(text = "Search Notes..",
//                    modifier = Modifier.alpha(0.5f), color = MaterialTheme.colorScheme.onSurface),
//                textStyle = MaterialTheme.typography.labelSmall,
//                singleLine = true,
//                leadingIcon =
//                    IconButton(
//                        modifier = Modifier
//                            .alpha(0.5f),
//                        onClick = {}
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Search Icon",
//                            tint = Color.White
//                        )
//                    }
//                ,
//                trailingIcon =
//                    IconButton(
//                        onClick = {
//                            if (text.isNotEmpty()) {
//                                onTextChange("")
//                            } else {
//                                onCloseClicked()
//                            }
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Close Icon",
//                            tint = Color.White
//                        )
//                    }
//                ,keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Search
//                ),
//                keyboardActions = KeyboardActions(
//                    onSearch = {
//                        onSearchClicked(text)
//                    }
//                ),
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.Transparent,
//                    cursorColor = Color.White.copy(alpha = 0.5f)
//                )
//
//            )