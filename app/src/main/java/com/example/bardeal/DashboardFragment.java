package com.example.bardeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.BitSet;

public class DashboardFragment extends Fragment {

    private static TextView name;
    private ImageView menu;
    private Context context;
    private static Context useInStaticClassContext;


    private static FirebaseUser user;
    private static StorageReference storageReference;
    private FirebaseStorage storage;

    private ModelBottomSheetChangeDisplayName modelBottomSheet;
    private ModelBottomSheetChangeCategories bottomSheetChangeCategories;
    private ModelBottomSheetChangeImageDisplay bottomSheetChangeImageDisplay;


    public DashboardFragment(Context context) {
        super(R.layout.dashboard_fragment);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menu = view.findViewById(R.id.menu_for_some_works);
        name = view.findViewById(R.id.nameOfUser);
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        useInStaticClassContext = context;

//        if (storageReference.child("UserProfileImages/"+
//                user.getUid()+".jpg") == null
//                && storageReference
//         == null){
//            storageReference.child("UserProfileImages");
//            storageReference.child("UserProfileImages/"+user.getUid()+".jpg");
//        }

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
                        Toast.makeText(getContext(), "Please Wait ...", Toast.LENGTH_LONG)
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
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext()
                                                    , "Change Name Successful"
                                                    , Toast.LENGTH_SHORT).show();

                                            name.setText(inputLayout.getEditText()
                                                    .getText().toString());
                                        } else {
                                            Toast.makeText(getContext()
                                                    , "Please Check vpn connection!"
                                                    , Toast.LENGTH_LONG)
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
                    .inflate(R.layout.pick_image_from_gallery_change_photo, container, false);

            // todo : change image prof
            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Uri selectedImageUri = data.getData();
                                try {
                                    Toast.makeText(getContext() ,selectedImageUri.toString() ,Toast.LENGTH_LONG).show();
                                    StorageReference dirRef = storageReference
                                            .child("UserProfileImages");
                                    StorageReference ref = storageReference
                                            .child("UserProfileImages/"+user.getUid()+".jpg");

                                    UploadTask uploadTaskProf = ref.putFile(selectedImageUri);

                                    Task<Uri> uriTask = uploadTaskProf.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                                            Task<Uri>>() {
                                        @NonNull
                                        @Override
                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            if (task.isSuccessful()){
                                                throw task.getException();
                                                // TODO : problem in here :/
                                            }

                                            return ref.getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                Uri resultUrl = task.getResult();
                                                String url = "https://firebase.google.com/docs/auth/android/twitter-login?authuser=0";
                                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                                CustomTabsIntent customTabsIntent = builder.build();
                                                customTabsIntent.launchUrl(useInStaticClassContext ,Uri.parse(url));
                                            } else {
                                                Toast.makeText(getContext() ,task.getException().toString() ,Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        }
                                    });
                                    // TODO : ADD TO FIREBASE STORAGE AND FROM THIS ADD TO IMAGE PROFILE
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), e.toString()
                                            , Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        }
                    });

            Button button = view.findViewById(R.id.goToStorage);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK
                            , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    activityResultLauncher.launch(intent);

                }
            });


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
