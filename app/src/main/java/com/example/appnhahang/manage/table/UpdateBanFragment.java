package com.example.appnhahang.manage.table;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhahang.R;
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


public class UpdateBanFragment extends Fragment {
    private static final int RESULT_OK = -1;
    Button btn;
    TextInputLayout edchiTiet;
    TextView tvmaBan;
    ImageView imgquanLyBan,imgcamera;
    RadioButton rabdat,rabtrong,rabdangXuLy;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    String chon="";
    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;

    String maban,img,chitiet;
    public UpdateBanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_ban, container, false);

        btn=view.findViewById(R.id.btnsua);

        tvmaBan=view.findViewById(R.id.tvmaBan);
        edchiTiet=view.findViewById(R.id.edchiTiet);
        imgquanLyBan=view.findViewById(R.id.imgquanLyBan);
        imgcamera=view.findViewById(R.id.imgcamera);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        rabdat=view.findViewById(R.id.rabdat);
        rabtrong=view.findViewById(R.id.rabtrong);
        rabdangXuLy=view.findViewById(R.id.rabdangXuLy);


        Bundle b=getArguments();
        maban=b.getString("maban");
        img=b.getString("img");
        chon=b.getString("trangthai");
        chitiet=b.getString("ghichu");

        tvmaBan.setText(maban);
        edchiTiet.getEditText().setText(chitiet);
        Log.e("anh", "ảnh:="+img );
        Picasso.get().load(img).into(imgquanLyBan);

        storageRef = storage.getReference();
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaBan();
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
                imgquanLyBan.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void SuaBan() {


        if(rabdat.isChecked()){
            chon="Đặt";
        }else  if(rabdangXuLy.isChecked()){
            chon="Đang sử lý";
        }else {
            chon="Trống";
        }




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
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onFailure: " +e.getMessage() );
                }
            });
            imgquanLyBan.setDrawingCacheEnabled(true);
            imgquanLyBan.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgquanLyBan.getDrawable()).getBitmap();
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

                        String chitiet1=edchiTiet.getEditText().getText().toString();

                        String key=maban;
                        DatabaseReference myRef=database.getReference("QuanLyBan");
                        myRef.child(key).child("anhban").setValue(linkanh);
                        myRef.child(key).child("ghichu").setValue(chitiet1);
                        myRef.child(key).child("trangthai").setValue(chon);

//                   finish();
                        Toast.makeText(getContext(), "Sửa Thành công", Toast.LENGTH_SHORT).show();
                        DanhSachBanFragment fragmentOne = new DanhSachBanFragment();
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
        imgquanLyBan.setDrawingCacheEnabled(true);
        imgquanLyBan.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgquanLyBan.getDrawable()).getBitmap();
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

                    String chitiet1=edchiTiet.getEditText().getText().toString();

                    String key=maban;
                    DatabaseReference myRef=database.getReference("QuanLyBan");
                    myRef.child(key).child("anhban").setValue(linkanh);
                    myRef.child(key).child("ghichu").setValue(chitiet1);
                    myRef.child(key).child("trangthai").setValue(chon);
                    Toast.makeText(getContext(), "Sửa Thành công", Toast.LENGTH_SHORT).show();
                    DanhSachBanFragment fragmentOne = new DanhSachBanFragment();
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