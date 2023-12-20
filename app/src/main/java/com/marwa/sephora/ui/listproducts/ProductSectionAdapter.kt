package  com.marwa.sephora.ui.listproducts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marwa.sephora.R
import com.marwa.sephora.databinding.ItemProductSectionBinding
import com.marwa.sephora.ui.model.ProductsReviewUiModel
import com.marwa.sephora.ui.model.ReviewUiModel
import javax.inject.Inject


class ProductSectionAdapter @Inject constructor() :
    ListAdapter<ProductsReviewUiModel, ProductSectionAdapter.ProductSectionVH>(ProductsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSectionVH {
        val binding =
            ItemProductSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductSectionVH(binding)
    }

    override fun onBindViewHolder(holder: ProductSectionVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductSectionVH(val binding: ItemProductSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productsReview: ProductsReviewUiModel) {
            initRecyclerView(productsReview.reviews)
            productsReview.product?.imageUrl?.let { loadImage(it) }
            with(binding) {
                productsReviewUiModel = productsReview
                icAction.setOnClickListener {
                    productsReview.isExpanded = !productsReview.isExpanded
                    notifyItemChanged(layoutPosition)
                }
            }

        }

        private fun loadImage(imageUrl: String) {
            Glide.with(binding.productBrand.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_card_brand_dk)
                .into(binding.productBrand)
        }

        private fun initRecyclerView(reviews: List<ReviewUiModel>?) {
            val reviewsAdapter = ReviewsAdapter()

            with(binding.reviewsList) {
                layoutManager = LinearLayoutManager(context)
                val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                AppCompatResources.getDrawable(context, R.drawable.divider)
                    ?.let { itemDecoration.setDrawable(it) }
                addItemDecoration(itemDecoration)
                adapter = reviewsAdapter
            }
            reviewsAdapter.submitList(reviews)
        }
    }

    class ProductsDiffCallback : DiffUtil.ItemCallback<ProductsReviewUiModel>() {

        override fun areItemsTheSame(
            oldItem: ProductsReviewUiModel,
            newItem: ProductsReviewUiModel
        ) = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ProductsReviewUiModel,
            newItem: ProductsReviewUiModel
        ) = oldItem == newItem

    }

}