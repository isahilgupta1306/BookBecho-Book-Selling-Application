package com.example.bookbecho.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookbecho.MainActivity;
import com.example.bookbecho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.security.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addProductForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addProductForm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addProductForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addProductForm.
     */
    // TODO: Rename and change types and number of parameters
    public static addProductForm newInstance(String param1, String param2) {
        addProductForm fragment = new addProductForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    required variables
    private ImageView productPhoto;
    private Button upload, submit;
    private Uri imageUri;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Products");
    private StorageReference folder = FirebaseStorage.getInstance().getReference().child("Images");
    private TextInputEditText prodTitle, prodDesc, prodPrice, UPIID;
    private String prodId;
    String imageStorageUrl = null;
    ProgressDialog progressDialog;
    String title, description, price, upiid;
    final int UPI_PAYMENT = 0;


    private static final int ImageBack = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        productPhoto = view.findViewById(R.id.product_photo);
        upload = view.findViewById(R.id.add_product_photo);
        submit = view.findViewById(R.id.submit);

        prodTitle = view.findViewById(R.id.prod_title);
        prodDesc = view.findViewById(R.id.prod_desc);
        prodPrice = view.findViewById(R.id.price);
        UPIID = view.findViewById(R.id.gpay_input);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, ImageBack);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateProduct();
//                payUsingUpi(UPIID.toString());

            }
        });

        return view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ImageBack && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            productPhoto.setImageURI(imageUri);
        }

        if(requestCode==UPI_PAYMENT && resultCode==RESULT_OK && data!=null){
            String text = data.getStringExtra("response");
            Log.d("UPI", "onActivityResult: "+text);
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add(text);
            String str = dataList.get(0);
            String paymentCancle = "";
            if(str==null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for(int i=0; i<response.length; i++){
                String equalStr[] = response[i].split("=");
                if(equalStr.length>=2){
                    if(equalStr[0].toLowerCase().equals("Status".toLowerCase())){
                        status = equalStr[1].toLowerCase();
                    } else if(equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase())||equalStr[0].toLowerCase().equals("txnRef".toLowerCase())){
                        approvalRefNo=equalStr[1];
                    }
                } else {
                    paymentCancle = "Payment Cancelled";
                }
            }

            if(status.equals("success")){
                Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
                validateProduct();
            } else if(paymentCancle.equals("Payment Cancelled")){
                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Transaction failed!!", Toast.LENGTH_SHORT).show();
            }

        }

    }



    private void validateProduct(){
        title = prodTitle.getText().toString();
        description = prodDesc.getText().toString();
        price = prodPrice.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        upiid = UPIID.getText().toString();
        String userId = user.getUid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        prodId = format;

        if (imageUri == null) {
            Toast.makeText(getContext(), "Please add image", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(description) || TextUtils.isEmpty(title) ||TextUtils.isEmpty(price)){
            Toast.makeText(getContext(), "Each field is mandatory", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            StorageReference ImageName = folder.child("image"+ imageUri.getLastPathSegment());

            ImageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog = new ProgressDialog(getContext());
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_layout);
                            imageStorageUrl = String.valueOf(uri);
                            HashMap<String, String> prodMap = new HashMap<>();
                            prodMap.put("title", title);
                            prodMap.put("description", description);
                            prodMap.put("price", price);
                            prodMap.put("photo", imageStorageUrl);
                            prodMap.put("user", userId);
                            prodMap.put("sold", "null");
                            prodMap.put("prodID", prodId);
                            prodMap.put("upiid", upiid);

                            root.push().setValue(prodMap);
                            FirebaseDatabase.getInstance().getReference().child("Added-Products").child(userId).push().setValue(prodMap);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Product Listed successfully", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(getContext() , MainActivity.class);
                            startActivity(i1);
                        }
                    });

                }
            });


        }
    }


    void payUsingUpi(String upiId){


                        Uri uri = Uri.parse("upi://pay").buildUpon()
                                .appendQueryParameter("pa", "sg7544815-1@okhdfcbank")
                                .appendQueryParameter("pn", "Sahil Gupta")
                                .appendQueryParameter("tn", "Payment for Product Selling")
                                .appendQueryParameter("am", "1")
                                .appendQueryParameter("cu", "INR")
                                .build();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);

                        Intent chooser = Intent.createChooser(intent, "Pay With");

                        if(null!= chooser.resolveActivity(getActivity().getPackageManager())){
                            startActivityForResult(chooser, UPI_PAYMENT);
                        } else {
                            Toast.makeText(getContext(), "No upi app found!", Toast.LENGTH_SHORT).show();
                        }



    }
}