package com.example.bardeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class DashboardFragment extends Fragment {

    private static TextView name;
    private ImageView menu;


    private static FirebaseUser user;
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
        if (user.getDisplayName() == "") {
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

    private void showMenu(View v, @MenuRes int menuRes) {
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
                            , "Display Name will change");
                    return true;
                } else if (menuItem.getItemId() == R.id.change_image_prof) {
                    // todo : change display Photo
                    bottomSheetChangeImageDisplay = new ModelBottomSheetChangeImageDisplay();
                    bottomSheetChangeImageDisplay.show(getActivity()
                                    .getSupportFragmentManager()
                            , "ImageDisplay will change");

                    return true;
                } else if (menuItem.getItemId() == R.id.change_categories) {
                    // todo : change categories selected
                    bottomSheetChangeCategories = new ModelBottomSheetChangeCategories();
                    bottomSheetChangeCategories.show(getActivity()
                                    .getSupportFragmentManager()
                            , "Categories will change");

                    return true;
                }

                return false;
            }
        });


        popupMenu.show();
    }

    public static class ModelBottomSheetChangeDisplayName extends BottomSheetDialogFragment {

        private TextInputLayout inputLayout;
        public Button buttonSet;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet, container, false);

            inputLayout = view.findViewById(R.id.textInputLayout);
            buttonSet = view.findViewById(R.id.setName);

            buttonSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputLayout.getEditText().getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Input Layout is Empty!"
                                , Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(getContext() ,"Please Wait ..." , Toast.LENGTH_LONG)
                                .show();
                        UserProfileChangeRequest userProfileChangeRequest =
                                new UserProfileChangeRequest.Builder()
                                        .setDisplayName(inputLayout.getEditText()
                                                .getText().toString())
                                        .build();


                        user.updateProfile(userProfileChangeRequest)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext()
                                                    ,"Change Name Successful"
                                                    ,Toast.LENGTH_SHORT).show();

                                            name.setText(inputLayout.getEditText()
                                                    .getText().toString());
                                        } else {
                                            Toast.makeText(getContext()
                                                          , "Please Check vpn connection!"
                                                    ,Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                });
                    }
                }
            });

            return view;
        }
    }

    public static class ModelBottomSheetChangeImageDisplay extends BottomSheetDialogFragment {


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {


            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet, container, false);



            return view;
        }
    }

    public static class ModelBottomSheetChangeCategories extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.change_name_bottom_sheet, container, false);

            return view;
        }
    }

}
