package com.example.bardeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class DashboardFragment extends Fragment {

    private ImageView profileImage;
    private TextView name;
    private TextView dateOfJoinToBardeal;
    private ImageView menu;

    private FirebaseUser user;
    private ModelBottomSheet modelBottomSheet;

    public DashboardFragment() {
        super(R.layout.dashboard_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menu = view.findViewById(R.id.menu_for_some_works);
        profileImage = view.findViewById(R.id.profileImage);
        name = view.findViewById(R.id.nameOfUser);
        dateOfJoinToBardeal = view.findViewById(R.id.dataOfJoin);
        user = FirebaseAuth.getInstance().getCurrentUser();

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
                    modelBottomSheet = new ModelBottomSheet();
                    modelBottomSheet.show(getActivity().getSupportFragmentManager() ,"goood");
                    return true;
                } else if (menuItem.getItemId() == R.id.change_image_prof) {
                    // todo : change display Photo

                    return true;
                } else if (menuItem.getItemId() == R.id.change_categories) {
                    // todo : change categories selected

                    return true;
                }

                return false;
            }
        });


        popupMenu.show();
    }

       public static class ModelBottomSheet extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet ,container ,false);

            return view;
        }
    }

}
