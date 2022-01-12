package com.example.bardeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardFragment extends Fragment {

    private ImageView profileImage;
    private TextView name;
    private TextView dateOfJoinToBardeal;
    private ImageView menu;
    private RadioGroup categoriesSelected;

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private ModelBottomSheet modelBottomSheet;

    public DashboardFragment() {
        super(R.layout.dashboard_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profileImage);
        name = view.findViewById(R.id.nameOfUser);
        dateOfJoinToBardeal = view.findViewById(R.id.dataOfJoin);
        categoriesSelected = view.findViewById(R.id.radioGroup);

        recyclerView = view.findViewById(R.id.recyclerviewForStoreSelectCategories);


    }

    @Override
    public void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();

        // TODO : get and Set profile image and set name ...

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelBottomSheet = new ModelBottomSheet();
                modelBottomSheet.show(getActivity().getSupportFragmentManager() ,"goood");
            }
        });

    }

   public static class ModelBottomSheet extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.cashing_fragment ,container ,false);

            return view;
        }
    }
}
