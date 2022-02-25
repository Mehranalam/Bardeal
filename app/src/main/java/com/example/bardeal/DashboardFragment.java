package com.example.bardeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardFragment extends Fragment {

    private TextView name;
    private ImageView menu;

    private FirebaseUser user;
    private ModelBottomSheetChangeDisplayName modelBottomSheet;
    private ModelBottomSheetChangeCategories bottomSheetChangeCategories;
    private ModelBottomSheetChangeImageDisplay bottomSheetChangeImageDisplay;

    public DashboardFragment() {
        super(R.layout.dashboard_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menu = view.findViewById(R.id.menu_for_some_works);
        name = view.findViewById(R.id.nameOfUser);
        user = FirebaseAuth.getInstance().getCurrentUser();

        // get name from firebase server and set this to text view for
        // name

        // if name from firebase is empty set (tab to set your name)
        if (user.getDisplayName() == null) {
            name.setText("Display Name is Empty");
        } else {
            name.setText(user.getDisplayName());
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show menu
                showMenu(view, R.menu.menu_for_dashboard_a_some_work);
            }
        });
    }

    private void showMenu(View v,@MenuRes int menuRes){
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.change_name) {
                    // todo : change display name
                    modelBottomSheet = new ModelBottomSheetChangeDisplayName();
                    modelBottomSheet.show(getActivity()
                            .getSupportFragmentManager()
                            ,"Display Name will change");
                    return true;
                } else if (menuItem.getItemId() == R.id.change_image_prof) {
                    // todo : change display Photo
                    bottomSheetChangeImageDisplay = new ModelBottomSheetChangeImageDisplay();
                    bottomSheetChangeImageDisplay.show(getActivity()
                            .getSupportFragmentManager()
                            ,"ImageDisplay will change");

                    return true;
                } else if (menuItem.getItemId() == R.id.change_categories) {
                    // todo : change categories selected
                    bottomSheetChangeCategories = new ModelBottomSheetChangeCategories();
                    bottomSheetChangeCategories.show(getActivity()
                                    .getSupportFragmentManager()
                            ,"Categories will change");

                    return true;
                }

                return false;
            }
        });


        popupMenu.show();
    }

       public static class ModelBottomSheetChangeDisplayName extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet ,container ,false);

            return view;
        }
    }

    public static class ModelBottomSheetChangeImageDisplay extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet ,container ,false);

            return view;
        }
    }

    public static class ModelBottomSheetChangeCategories extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet ,container ,false);

            return view;
        }
    }

}
