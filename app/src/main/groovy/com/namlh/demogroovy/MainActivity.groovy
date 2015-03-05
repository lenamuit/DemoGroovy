package com.namlh.demogroovy

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.squareup.picasso.Picasso
import rx.android.observables.AndroidObservable

class MainActivity extends BaseActivity {

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @InjectView(R.id.recycle_view)
    RecyclerView recyclerView;

    def postsAdapter = new PostsAdapter()
    def loadFinish = { refreshLayout.refreshing = false }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        contentView = R.layout.main_activity
        SwissKnife.inject(this)
        SwissKnife.restoreState(this,savedInstanceState)
        recyclerView.layoutManager = new GridLayoutManager(this,1)
        recyclerView.adapter = postsAdapter
        refreshLayout.onRefreshListener = { loadData() }
    }

    def loadData(){
        AndroidObservable.bindActivity(this,tinhteService.getList())
            .flatMap({rx.Observable.from(it.getPosts())})
            .subscribe({
                postsAdapter.addPost(it)
            },loadFinish,loadFinish)
    }

    /**
     * an adapter
     */
    private static class PostsAdapter extends RecyclerView.Adapter<PostViewHolder>{

        def data = new ArrayList()

        /**
         * add item to adapter
         * @param post
         * @return
         */
        def addPost(post){
            data.add post
            notifyDataSetChanged()
        }

        @Override
        PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PostViewHolder(View.inflate(viewGroup.context, R.layout.item_post,null))
        }

        @Override
        void onBindViewHolder(PostViewHolder viewHolder, int i) {
            def post = data.get(i)
            viewHolder.tvTitle.text = post.title
            viewHolder.tvTitle.onClick {Toast.makeText(viewHolder.tvTitle.context,"click $post.title",Toast.LENGTH_LONG).show()}
            Picasso.with(viewHolder.imgThumbnail.context)
                .load(post.link_images[0])
                .into(viewHolder.imgThumbnail)
        }

        @Override
        int getItemCount() {
            return data.size()
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.tv_title)
        TextView tvTitle
        @InjectView(R.id.img_thumbnail)
        ImageView imgThumbnail

        PostViewHolder(View itemView) {
            super(itemView)
            SwissKnife.inject(this,itemView)
        }
    }
}