package com.carol.discos.caroldiscos.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carol.discos.caroldiscos.AlbumEditActivity;
import com.carol.discos.caroldiscos.db.AlbumDbHelper;
import com.carol.discos.caroldiscos.db.AlbumEntry;

import java.util.List;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder> {

    private final List<AlbumEntry> mValues;
    private final Activity mActivity;


    public AlbumRecyclerViewAdapter(List<AlbumEntry> items, Activity activity) {
        mValues = items;
        mActivity = activity;

        this.notifyDataSetChanged();
    }

    public void refresh() {
        AlbumDbHelper db = new AlbumDbHelper(this.mActivity);
        mValues.clear();
        mValues.addAll(db.select());

        this.notifyDataSetChanged();
    }


    @Override
    public AlbumRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

        return new AlbumRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlbumRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.mTitleView.setText(holder.mItem.title);
        holder.mDescriptionView.setText(holder.mItem.artist + " (" + holder.mItem.year + ")");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public AlbumEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            view.setOnCreateContextMenuListener(this);

            mTitleView = (TextView) view.findViewById(android.R.id.text1);
            mDescriptionView = (TextView) view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menu.setHeaderTitle("Select The Action");

            MenuItem edit = menu.add(0, 1, 0, "Edit");//groupId, itemId, order, title
            MenuItem delete = menu.add(0, 2, 0, "Delete");


            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == 1) {
                Intent intent = new Intent(mView.getContext(), AlbumEditActivity.class);
                intent.setAction(Intent.ACTION_EDIT);
                intent.putExtra(AlbumEntry.COLUMN_NAME_ID, mItem.id);
                intent.putExtra(AlbumEntry.COLUMN_NAME_TITLE, mItem.title);
                intent.putExtra(AlbumEntry.COLUMN_NAME_ARTIST, mItem.artist);
                intent.putExtra(AlbumEntry.COLUMN_NAME_YEAR, mItem.year);
                intent.putExtra(AlbumEntry.COLUMN_NAME_GENRE, mItem.genre);

                Activity activity  = AlbumRecyclerViewAdapter.this.mActivity;
                activity.startActivityForResult(intent, 0);
            }
            else if (item.getItemId() == 2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
                builder.setTitle("Exclusao");
                //define a mensagem
                builder.setMessage("Deseja deletar o Album '" + mItem.title + "'?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlbumDbHelper db = new AlbumDbHelper(mView.getContext());

                        db.delete(mItem.id);
                        db.close();

                        AlbumRecyclerViewAdapter adaptor = AlbumRecyclerViewAdapter.this;

                        adaptor.refresh();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
            }

            return false;
        }
    }
}