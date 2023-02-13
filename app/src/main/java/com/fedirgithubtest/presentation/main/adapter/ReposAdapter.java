package com.fedirgithubtest.presentation.main.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fedirgithubtest.R;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.databinding.ItemRepoViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder> {


    public interface OnItemInteractionListener {
        void onFavoriteClicked(boolean isFavorite, GHRepo repo);

        void onItemClicked(boolean isFavorite, GHRepo repo);
    }

    private Map <Long, String> favMap = new HashMap<>();
    private List<GHRepo> dataList = new ArrayList<>();
    private boolean favListMode;

    private final OnItemInteractionListener listener;

    public void setDataList(List<GHRepo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void  addData(List<GHRepo> moreData) {
        int startPos = dataList.size();
        dataList.addAll(moreData);
        notifyItemRangeInserted(startPos, moreData.size());
    }

    public ReposAdapter(OnItemInteractionListener listener) {
        this(listener, false);
    }

    public ReposAdapter(OnItemInteractionListener listener, boolean favList) {
        this.listener = listener;
        this.favListMode = favList;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoViewHolder(
                ItemRepoViewBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        GHRepo repo = dataList.get(position);
        boolean isFav = favListMode;
        if(!favListMode && !favMap.isEmpty())
            isFav = favMap.containsKey(repo.getId());
        holder.bind(dataList.get(position), isFav);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {

        ItemRepoViewBinding binding;

        private boolean favorite = false;
        private GHRepo repo = null;

        public RepoViewHolder(@NonNull ItemRepoViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GHRepo repoIn, boolean fav) {
            this.favorite = fav;
            updateFavIcon();
            repo = repoIn;
            if (repo != null) {
                if (!TextUtils.isEmpty(repo.getOwner().getAvatarUrl())
                        && URLUtil.isValidUrl(repo.getOwner().getAvatarUrl())) {
                    Glide.with(binding.getRoot().getContext())
                            .load(repo.getOwner().getAvatarUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .into(binding.ivAvatar);
                }
                binding.tvRepoName.setText(repo.getOwner().getLogin());
                binding.tvRepoDescription.setText(repo.getDescription());
                binding.tvRepoStars.setText(String.valueOf(repo.getStargazersCount()));

                binding.ivFav.setOnClickListener(v -> {
                    favorite = !favorite;
                    updateFavIcon();
                    if(favorite){
                        favMap.put(repo.getId(), repo.getName());
                    } else {
                        favMap.remove(repo.getId());
                    }
                    if(favListMode) {
                        int pos = dataList.indexOf(repoIn);
                        dataList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                    if (listener != null) {
                        listener.onFavoriteClicked(favorite, repo);
                    }
                });

                binding.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onItemClicked(favorite, repo);
                    }
                });
            }
        }

        private void updateFavIcon() {
            if (favorite) {
                Glide.with(binding.getRoot().getContext())
                        .load(R.drawable.ic_baseline_favorite_24)
                        .into(binding.ivFav);
            } else {
                Glide.with(binding.getRoot().getContext())
                        .load(R.drawable.ic_baseline_favorite_border_24)
                        .into(binding.ivFav);
            }
        }
    }

    public List<GHRepo> getData() {
        return dataList;
    }

    public void setFavDataList(List<GHRepo> repos) {
        if(favListMode) return;
        favMap = new HashMap<>();
        if(repos != null && !repos.isEmpty()) {
            for (GHRepo repo  : repos){
                favMap.put(repo.getId(), repo.getName());
            }
        }
        notifyDataSetChanged();
    }

}
