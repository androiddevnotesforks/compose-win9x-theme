package nl.ncaj.theme.win9x

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.unit.dp
import nl.ncaj.theme.win9x.component.IcoImage
import nl.ncaj.theme.win9x.controls.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import win9x.components_overview.generated.resources.Res

@Composable
fun Overview(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier
    ) {
        item { ExampleItem("Button") { ButtonExample() } }
        item { ExampleItem("Option set") { OptionSetButtonExample() } }
        item { ExampleItem("Menu button") { MenuButtonExample() } }
        item { ExampleItem("Option button") { OptionButtonExample() } }
        item { ExampleItem("CheckBox") { CheckboxExample() } }
        item { ExampleItem("TextBox") { TextBoxExample() } }
        item { ExampleItem("Slider") { SliderExample() } }
        item { ExampleItem("ListBox") { ListBoxExample() } }
        item { ExampleItem("Scrollbar") { ScrollbarExample() } }
        item { ExampleItem("SpinBox") { SpinBoxExample() } }
        item { ExampleItem("TreeView") { TreeViewExample() } }
        item { ExampleItem("Tabs") { TabsExample() } }
        item { ExampleItem("Progress Indicator") { ProgressIndicatorExample() } }
        item { ExampleItem("Dropdown ListBox") { DropDownListBoxExample() } }
        item { ExampleItem("ComboBox") { ComboBoxExample() } }
        item { ExampleItem("DropDownComboBox") { DropDownComboBoxExample() } }
        item { ExampleItem("ListView") { ListViewExample() } }
    }
}

@Composable
private fun ExampleItem(
    label: String,
    content: @Composable () -> Unit,
) {
    Box(Modifier.size(200.dp).padding(4.dp)) {
        Grouping(
            label = label,
            modifier = Modifier.fillMaxSize(),
            labelAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.matchParentSize(),
                content = { content() }
            )
        }
    }
}

@Composable
private fun ButtonExample() {
    Column {
        Button(
            onClick = {},
        ) { Text("Default") }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {},
            enabled = false
        ) { Text("Disabled", enabled = false) }
    }
}

@Composable
private fun OptionSetButtonExample() {
    var set by remember { mutableStateOf(false) }
    Column {
        OptionSetButton(set = set, { set = it }) {
            Text(if (set) "Set" else "Not set")
        }
        Spacer(modifier = Modifier.height(2.dp))
        OptionSetButton(set = true, {}, enabled = false) {
            Text("Set Disabled", enabled = false)
        }
    }
}

data object SubMenuID1
data object SubMenuID2
data object SubMenuID3

@Composable
private fun MenuButtonExample() {
    MenuButton(
        label = { Text("Menu") },
        subMenu = { id ->
            when (id) {
                SubMenuID1 -> {
                    MenuItemLabel("Sub command 1") {}
                    MenuItemCascade("Sub menu3", modifier = Modifier.cascade(SubMenuID3))
                }

                SubMenuID2 -> {
                    MenuItemLabel("Sub command 2") {}
                }

                SubMenuID3 -> {
                    MenuItemLabel("Sub command 3") {}
                }
            }
        }
    ) {
        var optionChecked by remember { mutableStateOf(true) }
        var boxChecked by remember { mutableStateOf(true) }

        MenuItemLabel("Command") {}
        MenuItemOptionButton("Option button", optionChecked) { optionChecked = it }
        MenuItemCascade("Sub menu1", modifier = Modifier.cascade(SubMenuID1))
        MenuItemCheckBox("Checkbox", boxChecked) { boxChecked = it }
        MenuItemCascade("Sub menu2", modifier = Modifier.cascade(SubMenuID2))
    }
}

@Composable
private fun OptionButtonExample() {
    var checked by remember { mutableStateOf(true) }
    Column {
        OptionButton(checked = checked, onCheckChange = { checked = it }) {
            Text("Default")
        }
        OptionButton(checked = false, onCheckChange = {}, enabled = false) {
            Text("Disabled", enabled = false)
        }
        OptionButton(checked = true, onCheckChange = {}, enabled = false) {
            Text("Disabled checked", enabled = false)
        }
    }
}

@Composable
private fun CheckboxExample() {
    var checked by remember { mutableStateOf(true) }
    Column {
        Checkbox(checked = checked, onCheckChange = { checked = it }) {
            Text("Default")
        }
        Checkbox(checked = false, onCheckChange = {}, enabled = false) {
            Text("Disabled", enabled = false)
        }
        Checkbox(checked = true, onCheckChange = {}, enabled = false) {
            Text("Disabled checked", enabled = false)
        }
    }
}

@Composable
private fun TextBoxExample() {
    var text by remember { mutableStateOf("Default") }
    Column {
        TextBox(text, onValueChange = { text = it }, maxLines = 1)
        Spacer(modifier = Modifier.height(2.dp))
        TextBox("Disabled", onValueChange = {}, enabled = false)
    }
}

@Composable
private fun SliderExample() {
    Slider(steps = 4, onStep = { })
}

@Composable
private fun ListBoxExample() {
    var state = rememberListBoxState(itemCount = 5)
    ListBox(
        state = state,
        modifier = Modifier.fillMaxSize(0.6f),
    ) { index ->
        Text(
            modifier = Modifier
                .listBoxItem(enabled = index != 2)
                .fillMaxWidth()
                .padding(4.dp),
            text = "Value ${index + 1}",
            enabled = index != 2,
            color = if (state.selectedIndex == index) ColorProducer { Color.White } else null
        )
    }
}

@Composable
private fun ScrollbarExample() {
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()
    val horizontalScrollbarAdapter = rememberScrollbarAdapter(horizontalScrollState)
    val verticalScrollbarAdapter = rememberScrollbarAdapter(verticalScrollState)

    ScrollableHost(
        modifier = Modifier
            .sunkenBorder()
            .padding(Win9xTheme.borderWidthDp),
        horizontalScrollbarAdapter = horizontalScrollbarAdapter,
        verticalScrollbarAdapter = verticalScrollbarAdapter,
    ) {
        Text(
            text = "Some text that is repeated multiple times\n".repeat(15),
            modifier = Modifier
                .background(Win9xTheme.colorScheme.buttonHighlight)
                .padding(4.dp)
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
        )
    }
}

@Composable
private fun SpinBoxExample() {
    val decimalRegex = """[^0-9]""".toRegex()
    var value by remember { mutableIntStateOf(1) }

    SpinBox(
        value.toString(),
        onValueChange = {
            value = decimalRegex.replace(it, "").takeIf { it.isNotBlank() }?.toInt() ?: 0
        },
        onIncrease = { value++ },
        onDecrease = { value-- },
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TreeViewExample() {
    var collapsable by remember { mutableStateOf(true) }
    var showRelationship by remember { mutableStateOf(true) }

    fun TreeViewScope.labelItem(
        label: String,
        enabled: Boolean = true,
        children: (TreeViewScope.() -> Unit)? = null
    ) = item(label, children) {
        TreeViewItem(
            label = label,
            enabled = enabled,
            leadingIcon = {
                val icon = suspend { Res.readBytes("files/directory_open.ico") }
                IcoImage(icon, contentDescription = null)
            },
            onClick = { },
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TreeView(
            collapsable = collapsable,
            showRelationship = showRelationship,
            modifier = Modifier.fillMaxWidth().weight(1f),
        ) {
            labelItem("Value 1")
            labelItem("Value 2", enabled = false)
            labelItem("Value 3") {
                labelItem("Value 3.1")
                labelItem("Value 3.2") {
                    labelItem("Value 3.2.1") {
                        labelItem("Value 3.2.1.1")
                        labelItem("Value 3.2.1.2") {
                            labelItem("Value 3.2.1.2.1")
                            labelItem("Value 3.2.1.2.2")
                        }
                        labelItem("Value 3.2.1.3")
                    }
                    labelItem("Value 3.2.2") {
                        labelItem("Value 3.2.2.1")
                        labelItem("Value 3.2.2.2") {
                            labelItem("Value 3.2.2.2.1") {
                                labelItem("Value 3.2.2.2.1.1")
                            }
                        }
                    }
                }
                labelItem("Value 3.3")
                labelItem("Value 3.4") {
                    labelItem("Value 3.4.1")
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Checkbox(
                checked = collapsable,
                onCheckChange = { collapsable = it },
                label = { Text("Collapsable") }
            )
            Spacer(Modifier.width(4.dp))
            Checkbox(
                checked = showRelationship,
                onCheckChange = { showRelationship = it },
                label = { Text("Relationship") }
            )
        }
    }
}

@Composable
private fun TabsExample() {
    var selectedTabIndex by remember { mutableIntStateOf(1) }

    TabHost(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { selectedTabIndex = it },
        tabCount = 3,
        tabs = { Text("Tab ${it + 1}") },
        modifier = Modifier.fillMaxSize(),
        content = {
            Text(
                text = "Tab selected: ${selectedTabIndex + 1}",
                modifier = Modifier.fillMaxSize()
            )
        },
    )
}

@Composable
private fun ProgressIndicatorExample() {
    ProgressIndicator(progress = 0.5f)
}

@Composable
private fun DropDownListBoxExample() {
    var expanded by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf("Value") }
    DropDownListBox(
        text = currentValue,
        expanded = expanded,
        onExpandChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        DropDownListBoxItem(text = "Value", onSelection = { currentValue = "Value" })
        DropDownListBoxItem(text = "Value (disabled)", onSelection = { }, enabled = false)
        DropDownListBoxItem(text = "Longer value", onSelection = { currentValue = "Longer value" })
    }
}

@Composable
private fun ComboBoxExample() {
    var value by remember { mutableStateOf("") }
    var state = rememberListBoxState(itemCount = 3, defaultIndex = -1) { index -> value = "Value ${index+1}" }
    Column(Modifier.fillMaxSize(0.6f)) {
        TextBox(
            value = value,
            onValueChange = { value = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        ListBox(
            state = state,
            modifier = Modifier.fillMaxSize(),
        ) { index ->
            Text(
                modifier = Modifier
                    .listBoxItem(enabled = index != 1)
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Value ${index+1}",
                enabled = index != 1,
                color = if (state.selectedIndex == index) ColorProducer { Color.White } else null
            )
        }
    }
}

@Composable
private fun DropDownComboBoxExample() {
    var value by remember { mutableStateOf("") }
    var selection by remember { mutableStateOf(0) }
    DropDownComboBox(
        value = value,
        onValueChange = { value = it }
    ) {
        for (index in 0 until 3) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Value ${index+1}",
                enabled = index != 1,
                color = if (selection == index) ColorProducer { Color.White } else null
            )
        }
    }
}

enum class ListViewState { LargeIcon, SmallIcon, List, Details }
class ListViewItem(val label: String, val description: String, val data: String, val icon: suspend () -> ByteArray)

@OptIn(ExperimentalResourceApi::class)
val listViewItems = (0 until 20)
    .map { ListViewItem("Item $it", "Description", "data", suspend { Res.readBytes("files/directory_open.ico") }) }

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ListViewExample() {
    var selectedViewState by remember { mutableStateOf(ListViewState.LargeIcon) }

    Column {
        when (selectedViewState) {
            ListViewState.LargeIcon -> VerticalListView(
                modifier = Modifier.weight(1f)
            ) {
                listViewItems.forEach {
                    LargeIconListItem(
                        label = it.label,
                        icon = { IcoImage(it.icon, null) },
                    )
                }
            }

            ListViewState.SmallIcon -> VerticalListView(
                modifier = Modifier.weight(1f)
            ) {
                listViewItems.forEach {
                    SmallIconListItem(
                        label = it.label,
                        icon = { IcoImage(it.icon, null) },
                    )
                }
            }

            ListViewState.List -> HorizontalListView(
                modifier = Modifier.weight(1f)
            ) {
                listViewItems.forEach {
                    SmallIconListItem(
                        label = it.label,
                        icon = { IcoImage(it.icon, null) },
                    )
                }
            }

            ListViewState.Details -> DetailsListView(
                columns = 3,
                modifier = Modifier.weight(1f)
            ) {
                headingRow { column ->
                    val label = when (column) {
                        0 -> "Title"
                        1 -> "Description"
                        2 -> "Data"
                        else -> error("Column label not found")
                    }
                    ColumnHeading(
                        label = label,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                listViewItems.forEach {
                    itemRow { column ->
                        when (column) {
                            0 -> DetailsViewListItem(label = it.label, icon = { IcoImage(it.icon, null) })
                            1 -> DetailsViewListItem(label = it.description)
                            2 -> DetailsViewListItem(label = it.data)
                            else -> error("Column data not found")
                        }
                    }
                }
            }
        }
        FlowRow(
            modifier = Modifier.padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ListViewState.entries.forEachIndexed { index, viewState ->
                OptionButton(
                    checked = viewState == selectedViewState,
                    onCheckChange = { selectedViewState = viewState },
                    label = { Text(viewState.name) },
                    modifier = if (index == ListViewState.entries.size - 1) Modifier
                    else Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}
