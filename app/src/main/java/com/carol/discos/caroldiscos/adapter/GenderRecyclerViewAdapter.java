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

import com.carol.discos.caroldiscos.GenderEditActivity;
import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.db.GenderEntry;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GenderEntry}
 * TODO: Replace the implementation with code for your data type.
 */
public class GenderRecyclerViewAdapter extends RecyclerView.Adapter<GenderRecyclerViewAdapter.ViewHolder> {

    private final List<GenderEntry> mValues;
    private final Activity mActivity;


    public GenderRecyclerViewAdapter(List<GenderEntry> items, Activity activity) {
        mValues = items;
        mActivity = activity;

        this.notifyDataSetChanged();
    }

    public void refresh() {
        GenderDbHelper db = new GenderDbHelper(this.mActivity);
        mValues.clear();
        mValues.addAll(db.select());

        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        holder.mContentView.setText(mValues.get(position).description);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public GenderEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            view.setOnCreateContextMenuListener(this);

            mIdView = (TextView) view.findViewById(android.R.id.text1);
            mContentView = (TextView) view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
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
                Intent intent = new Intent(mView.getContext(), GenderEditActivity.class);
                intent.setAction(Intent.ACTION_EDIT);
                intent.putExtra(GenderEntry.COLUMN_NAME_NAME, mItem.name);
                intent.putExtra(GenderEntry.COLUMN_NAME_DESCRIPTION, mItem.description);
                intent.putExtra(GenderEntry.COLUMN_NAME_ID, mItem.id);

                Activity activity  = GenderRecyclerViewAdapter.this.mActivity;
                activity.startActivityForResult(intent, 0);
            }
            else if (item.getItemId() == 2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
                builder.setTitle("Exclusao");
                //define a mensagem
                builder.setMessage("Deseja deletar o Genero '" + mItem.name + "'?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GenderDbHelper db = new GenderDbHelper(mView.getContext());

                        db.delete(mItem.id);
                        db.close();

                        GenderRecyclerViewAdapter adaptor = GenderRecyclerViewAdapter.this;

                        adaptor.refresh();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GenderDbHelper db = new GenderDbHelper(mView.getContext());

                        db.delete(mItem.id);
                    }
                });

                builder.create().show();
            }

            return false;
        }
    }
}
