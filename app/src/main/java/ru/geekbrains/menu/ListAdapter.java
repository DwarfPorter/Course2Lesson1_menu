package ru.geekbrains.menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private int menuPosition;
    private List<String> data;
    private Fragment fargment;

    public ListAdapter(List<String> data, Fragment fargment){
        this.data = data;
        this.fargment = fargment;
    }

    // region Переопределение методов адаптера
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        // Заполнение элементов холдера
        TextView textElement = holder.getTextElement();
        textElement.setText(data.get(position));

        // Определяем текущую позицию в списке
        textElement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                menuPosition = position;
                return false;
            }
        });

        // Так регистрируется контекстное меню
        if (fargment != null){
            fargment.registerForContextMenu(textElement);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    // endregion

    public int getMenuPosition() {
        return menuPosition;
    }

    // region Изменение списка
    // Добавить элемент в список
    public void addItem(String element){
        data.add(element);
        notifyItemInserted(data.size()-1);
    }

    // Заменить элемент в списке
    public void updateItem(String element, int position){
        data.set(position, element);
        notifyItemChanged(position);
    }

    // Удалить элемент из списка
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    // Очистить список
    public void clearItems(){
        data.clear();
        notifyDataSetChanged();
    }
    // endregion

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textElement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textElement = itemView.findViewById(R.id.textElement);
        }

        public TextView getTextElement() {
            return textElement;
        }
    }
}
