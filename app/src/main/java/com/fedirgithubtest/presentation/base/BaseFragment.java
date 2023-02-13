package com.fedirgithubtest.presentation.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fedirgithubtest.R;
import com.fedirgithubtest.data.model.cache.ApiErrorType;
import com.fedirgithubtest.data.model.cache.InfoMessage;
import com.fedirgithubtest.presentation.main.MainActivity;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    protected VM viewModel;

    private final Observer errorObserver = (Observer<ApiErrorType>) this::onError;
    private final Observer infoMessageObserver = (Observer<InfoMessage>) this::onInfoMessage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel != null) viewModel.error.observe(getViewLifecycleOwner(), errorObserver);
        if (viewModel != null) viewModel.info.observe(getViewLifecycleOwner(), infoMessageObserver);
    }

    protected void onError(ApiErrorType errorType) {
        String msg;
        switch (errorType) {
            case INTERNET_CONNECTION:
                msg = getString(R.string.error_no_internet);
                break;
            case REQUESTS_LIMIT:
                msg = getString(R.string.error_request_limit);
                break;
            default:
                msg = getString(R.string.error_unknown);
                break;
        }
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void onInfoMessage(InfoMessage infoMessage) {
        String msg;
        switch (infoMessage.getInfoType()) {
            case NUMBER_ENTRIES:
                msg = getString(R.string.info_entries_found, infoMessage.getExtra());
                break;
            case FAVORITE_ADD:
                msg = getString(R.string.info_favorite_added);
                break;
            case FAVORITE_REMOVE:
                msg = getString(R.string.info_favorite_removed);
                break;
            case OTHER:
            default:
                msg = getString(R.string.error_unknown);
                break;
        }
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        if (viewModel != null) viewModel.error.removeObserver(errorObserver);
        if (viewModel != null) viewModel.info.removeObserver(infoMessageObserver);
        super.onDestroyView();
    }
}
