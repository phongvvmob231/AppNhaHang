package com.example.appnhahang.manage.menu;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.QuanLyMenu;
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


public class ThemMenuFragment extends Fragment {
    private static final int RESULT_OK =-1 ;
    TextInputLayout edMaMon,edTenMon,edGiaTien;
    ImageView imgQuanLyMenu,imgCamera;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;
    Button buttonaa;
    public ThemMenuFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_them_menu, container, false);

        edMaMon=view.findViewById(R.id.edMaMon);
        edTenMon=view.findViewById(R.id.edTenMon);
        edGiaTien=view.findViewById(R.id.edGiaTien);
        imgQuanLyMenu=view.findViewById(R.id.imgQuanLyMenu);
        imgCamera=view.findViewById(R.id.imgCameraMenu);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        buttonaa=view.findViewById(R.id.buttonaa);

        buttonaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuuMenu();
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                                selectedImage=data.getExtras().get("data");
                imgQuanLyMenu.setImageBitmap(selectedImage);
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


    private void chupanh() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private void LuuMenu() {

        StorageReference mountainsRef=storageRef.child("image/"+ UUID.randomUUID().toString());

        mountainsRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();

            }
        });
        imgQuanLyMenu.setDrawingCacheEnabled(true);
        imgQuanLyMenu.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgQuanLyMenu.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }


                return mountainsRef.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri= task.getResult();
                    linkanh=downloadUri.toString();

                    String mamon=edMaMon.getEditText().getText().toString();
                    String tenmon=edTenMon.getEditText().getText().toString();
                    String giatien=edGiaTien.getEditText().getText().toString();

                    String key=mamon;
                    DatabaseReference myRef1=database.getReference().child("QuanLyMenu");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(key)) {
                                Toast.makeText(getContext(), "Món ăn đã có", Toast.LENGTH_SHORT).show();
                            } else {

                                DatabaseReference myRef=database.getReference().child("QuanLyMenu").child(key);
                                QuanLyMenu quanLyMenu=new QuanLyMenu(mamon,tenmon,giatien,linkanh);
                                myRef.setValue(quanLyMenu).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

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

                }
            }
        });


    }
}