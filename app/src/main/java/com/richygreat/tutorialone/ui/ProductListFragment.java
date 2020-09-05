package com.richygreat.tutorialone.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.richygreat.tutorialone.R;
import com.richygreat.tutorialone.databinding.ProductListFragmentBinding;
import com.richygreat.tutorialone.db.entity.ProductEntity;
import com.richygreat.tutorialone.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductListFragment extends Fragment {
    private ProductAdapter mProductAdapter;
    private ProductListFragmentBinding mBinding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_list_fragment, container, false);
        mProductAdapter = new ProductAdapter();
        mBinding.productsList.setAdapter(mProductAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProductListViewModel viewModel =
                new ViewModelProvider(this).get(ProductListViewModel.class);
        subscribeUi(viewModel.getProducts());
    }

    private void subscribeUi(LiveData<List<ProductEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(getViewLifecycleOwner(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> myProducts) {
                if (myProducts != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(myProducts);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    @Override
    public void onDestroyView() {
        mBinding = null;
        mProductAdapter = null;
        super.onDestroyView();
    }
}
