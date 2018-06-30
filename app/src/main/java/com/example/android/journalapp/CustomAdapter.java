package com.example.android.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.journalapp.Database.NoteEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String DATE_FORMAT = "dd MMM";

    private Context context;

    final private ItemClickListener itemClickListener;
    private List<NoteEntry> noteEntries;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public CustomAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteEntry noteEntry = noteEntries.get(position);
        String note = noteEntry.getNotes();
        String title = noteEntry.getTitle();
        String created_at = dateFormat.format(noteEntry.getCreated_at());

        holder.txttitle.setText(title);
        holder.txtcreated_at.setText(created_at);
    }

    @Override
    public int getItemCount() {
        if (noteEntries == null) {
            return 0;
        }
        return noteEntries.size();
    }

    public List<NoteEntry> getTasks() {
        return noteEntries;
    }

    public void setTasks(List<NoteEntry> noteEntries) {
        this.noteEntries = noteEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txttitle,txtcreated_at;

        public ViewHolder(View itemView) {
            super(itemView);

            txttitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtcreated_at = (TextView) itemView.findViewById(R.id.txt_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = noteEntries.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(elementId);
        }
    }
}
