package com.example.appnhahang.manage.table;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.QuanLyBan;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;


public class ThemBanFragment extends Fragment {
    private static final int RESULT_OK = -1;
    TextInputLayout edMaBan,edChiTiet;
    ImageView imgQuanLyBan,imgCamera;
    RadioButton rabDat,rabTrong,rabDangXuLy;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    String chon="";
    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;
    Button btn;
    public ThemBanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_them_ban, container, false);

        edMaBan=view.findViewById(R.id.edMaBan);
        edChiTiet=view.findViewById(R.id.edChiTiet);
        imgQuanLyBan=view.findViewById(R.id.imgQuanLyBan);
        imgCamera=view.findViewById(R.id.imgCamera);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        storageRef = storage.getReference();
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });

        rabDat=view.findViewById(R.id.rabDat);
        rabTrong=view.findViewById(R.id.rabTrong);
        rabDangXuLy=view.findViewById(R.id.rabDangXuLy);

        btn=view.findViewById(R.id.btnthem);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuuBan();
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
                imgQuanLyBan.setImageBitmap(selectedImage);
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

    private void LuuBan() {


        StorageReference mountainsRef=storageRef.child("image/"+ UUID.randomUUID().toString());
        Log.e("TAG", "LuuBan: "+mountainsRef);
        Log.e("TAG", "ImG "+imageUri );
        mountainsRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //  Toast.makeText(ThemBanActivity.this, "Thannhcong", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                Log.e("TAG1", "onFailure: " +e.getMessage() );
            }
        });
        imgQuanLyBan.setDrawingCacheEnabled(true);
        imgQuanLyBan.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgQuanLyBan.getDrawable()).getBitmap();
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

                    if(rabDat.isChecked()){
                        chon="Đặt";
                    }else  if(rabDangXuLy.isChecked()){
                        chon="Đang sử lý";
                    }else {
                        chon="Trống";
                    }

                    String maban=edMaBan.getEditText().getText().toString();
                    String chitiet=edChiTiet.getEditText().getText().toString();

                    String key=maban;

                    DatabaseReference myRef1=database.getReference().child("QuanLyBan");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(key)) {
                                Toast.makeText(getContext(), "Bàn đã có", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference myRef=database.getReference().child("QuanLyBan").child(key);
                                QuanLyBan quanLyBan=new QuanLyBan(maban,linkanh,chon,chitiet);
                                myRef.setValue(quanLyBan).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.e("TAG", "dulieu== "+ quanLyBan);
                                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    // Handle failures
                    // ...
                }
            }
        });




    }


}