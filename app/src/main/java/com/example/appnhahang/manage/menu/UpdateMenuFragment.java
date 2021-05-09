package com.example.appnhahang.manage.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhahang.R;
import com.example.appnhahang.manage.table.DanhSachBanFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class UpdateMenuFragment extends Fragment {
    private static final int RESULT_OK = -1;
    TextInputLayout edtenmon,edgiatien;
    TextView tvmamon;
    ImageView imgquanLyMenu,imgcamera;

    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;

    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;
Button btn;
    String mamon,img,tenmon,giatien;

    public UpdateMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_menu, container, false);

        tvmamon=view.findViewById(R.id.tvmaMon);
        edtenmon=view.findViewById(R.id.edtenMon);
        edgiatien=view.findViewById(R.id.edgiaTien);
        imgquanLyMenu=view.findViewById(R.id.imgquanLyMenuSua);
        imgcamera=view.findViewById(R.id.imgcameraSua);
        FirebaseStorage storage = FirebaseStorage.getInstance();


        btn=view.findViewById(R.id.button2);
        Bundle b=getArguments();
        mamon=b.getString("mamon");
        img=b.getString("img");
        tenmon=b.getString("tenmon");
        giatien=b.getString("giatien");

        tvmamon.setText(mamon);
        edtenmon.getEditText().setText(tenmon);
        edgiatien.getEditText().setText(giatien);

        Log.e("anh", "ảnh:="+img );
        Picasso.get().load(img).into(imgquanLyMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaMon();
            }
        });
        storageRef = storage.getReference();
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });
        database = FirebaseDatabase.getInstance();


        return view;
    }

    private void chupanh() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                                selectedImage=data.getExtras().get("data");
                imgquanLyMenu.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }




    private void SuaMon() {


        StorageReference mountainsRef=storageRef.child("image/"+ UUID.randomUUID().toString());

        if(imageUri!=null){

            Log.e("TAG", "LuuBan: "+mountainsRef);
            mountainsRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    //  Toast.makeText(ThemBanActivity.this, "Thannhcong", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onCompl"+task.getResult() );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onFailure: " +e.getMessage() );
                }
            });
            imgquanLyMenu.setDrawingCacheEnabled(true);
            imgquanLyMenu.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgquanLyMenu.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

//                Toast.makeText(ThemBanActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(ThemBanActivity.this, "Thanh công", Toast.LENGTH_SHORT).show();
                    Log.e("TAG12", "imglik="+taskSnapshot.getMetadata() );
                }
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri= task.getResult();
                        linkanh=downloadUri.toString();
                        Log.e("TAG", "onComplete: "+downloadUri);


                        String tenmon1=edtenmon.getEditText().getText().toString();
                        String giatien1=edgiatien.getEditText().getText().toString();
                        String key=mamon;
                        DatabaseReference myRef=database.getReference("QuanLyMenu");
                        myRef.child(key).child("anhmon").setValue(linkanh);
                        myRef.child(key).child("tenmon").setValue(tenmon1);
                        myRef.child(key).child("gia").setValue(giatien1);

//                   finish();
                        Toast.makeText(getActivity(), "Sửa Thành công", Toast.LENGTH_SHORT).show();
                        DanhSachMenuFragment fragmentOne = new DanhSachMenuFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, fragmentOne);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        imgquanLyMenu.setDrawingCacheEnabled(true);
        imgquanLyMenu.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgquanLyMenu.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

//                Toast.makeText(ThemBanActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(ThemBanActivity.this, "Thanh công", Toast.LENGTH_SHORT).show();
                Log.e("TAG12", "imglik="+taskSnapshot.getMetadata() );
            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri= task.getResult();
                    linkanh=downloadUri.toString();
                    Log.e("TAG", "onComplete: "+downloadUri);

                    String tenmon1=edtenmon.getEditText().getText().toString();
                    String giatien1=edgiatien.getEditText().getText().toString();
                    String key=mamon;
                    DatabaseReference myRef=database.getReference("QuanLyMenu");
                    myRef.child(key).child("anhmon").setValue(linkanh);
                    myRef.child(key).child("tenmon").setValue(tenmon1);
                    myRef.child(key).child("gia").setValue(giatien1);

//                   finish();
                    Toast.makeText(getActivity(), "Sửa Thành công", Toast.LENGTH_SHORT).show();
                    DanhSachMenuFragment fragmentOne = new DanhSachMenuFragment();
                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, fragmentOne);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }



}