package util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.catalin.mo2o_test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by catalini on 23-Nov-17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>
{
	private static final String THUMBNAIL_KEY   = "thumbnail";
	private static final String TITLE_KEY       = "title";
	private static final String INGREDIENTS_KEY = "ingredients";

	private JSONArray mDataSet;
	private Context   mContext;

	public RecipeListAdapter(JSONArray data, Context context)
	{
		mDataSet = data;
		mContext = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext())
		                       .inflate(R.layout.recipe_list_item_layout, parent, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		String thumbnailUrl = "";
		String title        = "";
		String ingredients  = "";

		try
		{
			JSONObject recipe = mDataSet.getJSONObject(position);
			thumbnailUrl = recipe.getString(THUMBNAIL_KEY);
			title = recipe.getString(TITLE_KEY);
			ingredients = recipe.getString(INGREDIENTS_KEY);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		holder.getTitleTextView().setText(title);
		holder.getIngredientsTextView().setText(ingredients);
		holder.getThumbnailView().setDefaultImageResId(android.R.drawable.presence_offline);

		if (!TextUtils.isEmpty(thumbnailUrl))
		{
			holder.getThumbnailView().setImageUrl(thumbnailUrl, VolleySingleton
					.getInstance(mContext.getApplicationContext()).getImageLoader());
		}
	}

	@Override
	public int getItemCount()
	{
		return mDataSet.length();
	}

	public void setData(JSONArray data)
	{
		mDataSet = data;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		private NetworkImageView thumbnailView;
		private TextView         titleTextView;
		private TextView         ingredientsTextView;

		public ViewHolder(View v)
		{
			super(v);

			thumbnailView = v.findViewById(R.id.recipe_thumbnail);
			titleTextView = v.findViewById(R.id.recipe_title);
			ingredientsTextView = v.findViewById(R.id.recipe_description);
		}

		public NetworkImageView getThumbnailView()
		{
			return thumbnailView;
		}

		public TextView getTitleTextView()
		{
			return titleTextView;
		}

		public TextView getIngredientsTextView()
		{
			return ingredientsTextView;
		}
	}
}
