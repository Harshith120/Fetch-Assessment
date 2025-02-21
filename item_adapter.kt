import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val groupedItems: Map<Int, List<Item>>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val sortedKeys = groupedItems.keys.sorted()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listId = sortedKeys[position]
        val items = groupedItems[listId] ?: emptyList()

        holder.listIdTextView.text = "List ID: $listId"
        holder.itemsTextView.text = items.joinToString("\n") { "- ${it.name}" }
    }

    override fun getItemCount(): Int = sortedKeys.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listIdTextView: TextView = view.findViewById(R.id.listIdTextView)
        val itemsTextView: TextView = view.findViewById(R.id.itemsTextView)
    }
}
