package  com.marwa.sephora.ui.listproducts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marwa.sephora.databinding.ItemReviewBinding
import com.marwa.sephora.ui.model.ReviewUiModel


class ReviewsAdapter :
    ListAdapter<ReviewUiModel, ReviewsAdapter.ReviewViewHolder>(DynamicFAQDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReviewViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewUiModel) {
            binding.reviewUiModel = review
        }
    }


    class DynamicFAQDiffCallback : DiffUtil.ItemCallback<ReviewUiModel>() {

        override fun areItemsTheSame(
            oldItem: ReviewUiModel,
            newItem: ReviewUiModel
        ) = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ReviewUiModel,
            newItem: ReviewUiModel
        ) = oldItem == newItem
    }

}