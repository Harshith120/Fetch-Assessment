import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchItems()
    }

    private fun fetchItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getItems()

                // Filter out items where name is null or blank
                val filteredItems = response.filter { it.name?.isNotBlank() == true }

                // Sort items first by listId, then by name
                val sortedItems = filteredItems.sortedWith(compareBy({ it.listId }, { it.name }))

                // Group items by listId
                val groupedItems = sortedItems.groupBy { it.listId }

                withContext(Dispatchers.Main) {
                    adapter = ItemAdapter(groupedItems)
                    recyclerView.adapter = adapter
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching data: ${e.message}")
            }
        }
    }
}
