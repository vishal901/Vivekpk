package in.vaksys.vivekpk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Harsh on 27-01-2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private final Context context;
    private final RealmResults<UserImages> userImages;
    MyApplication myApplication;
    UserImages userImages1;
    private Realm realm;

    public ImageAdapter(Context context, RealmResults<UserImages> userImages) {
        this.context = context;
        this.userImages = userImages;
        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_image, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserImages images = userImages.get(position);
        holder.ImageUser.setImageBitmap(GetImageFromStream(images.getImages()));
        holder.ImgaeUUID.setText(images.getId());
        holder.ImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myApplication, "fuck", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap GetImageFromStream(String images) {
        myApplication.showLog("adapter ", "sakjdasdjhakjsdhkjah ");
        byte[] b = Base64.decode(images, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


    @Override
    public int getItemCount() {
        return (null != userImages ? userImages.size() : 0);
    }

    public void saveImageToDatabase(String bitmap, String ImageName) {

        try {
            realm.beginTransaction();
            userImages1 = realm.createObject(UserImages.class);
            userImages1.setId(String.valueOf(UUID.randomUUID()));
            userImages1.setImages(bitmap);
            realm.commitTransaction();
            notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.single_image_id)
        TextView ImgaeUUID;
        @Bind(R.id.single_image)
        ImageView ImageUser;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
