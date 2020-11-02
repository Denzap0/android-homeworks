import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework6.File
import com.example.homework6.ListItemActionListener
import com.example.homework6.R

class FileRecyclerAdapter (
    files: List<File>?,
    listItemActionListener: ListItemActionListener
) :
    RecyclerView.Adapter<FileRecyclerAdapter.ItemViewHolder>() {
    private val files: MutableList<File>? = ArrayList()
    private val listItemActionListener: ListItemActionListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file, parent, false)
        return ItemViewHolder(view, listItemActionListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(files!![position])
    }

    override fun getItemCount(): Int {
        return files?.size ?: 0
    }

    class ItemViewHolder(
        itemView: View,
        listItemActionListener: ListItemActionListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private var fileElement: LinearLayout = itemView.findViewById(R.id.fileComponent)
        private var fileIcon: ImageView = itemView.findViewById(R.id.fileImage)
        private var fileName: TextView = itemView.findViewById(R.id.fileName)
        private val listItemActionListener: ListItemActionListener?

        fun bind(file : File) {
            fileElement.setOnClickListener { listItemActionListener?.onItemClicked(file) }
            fileName.text = file.name
        }

        init {
            this.listItemActionListener = listItemActionListener
        }
    }

    init {
        this.files!!.addAll(files!!)
        this.listItemActionListener = listItemActionListener
    }
}