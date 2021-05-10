package njit.oa.nosnooze.alarm;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import njit.oa.nosnooze.db.entity.AlarmEntity;


public class AlarmListAdapter extends ListAdapter<AlarmEntity, AlarmViewHolder> {

    public AlarmListAdapter(@NonNull DiffUtil.ItemCallback<AlarmEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AlarmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        AlarmEntity current = getItem(position);
        holder.bind(current.hour, current.minute, current.created);
    }

    static class UserDiff extends DiffUtil.ItemCallback<AlarmEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem.getData().equals(newItem.getData());
        }
    }
}

