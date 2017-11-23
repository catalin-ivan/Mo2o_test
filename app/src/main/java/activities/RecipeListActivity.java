package activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.catalin.mo2o_test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Configuration;
import util.RecipeListAdapter;
import util.VolleySingleton;

/**
 * Created by catalini on 23-Nov-17.
 */

public class RecipeListActivity extends Activity
{
	private EditText     mSearchBox;
	private RecyclerView mRecipeList;
	private RecipeListAdapter mRecipeListAdapter;

	private TextWatcher mSearchBoxTextWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
		{

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
		{
			getRecipes(Configuration.RECIPE_URL + "?q=" + mSearchBox.getText());
		}

		@Override
		public void afterTextChanged(Editable editable)
		{

		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.recipe_activity_layout);
		mSearchBox = findViewById(R.id.search_box);
		mRecipeList = (RecyclerView) findViewById(R.id.recipe_list);
		//RecyclerView must have a LayoutManager
		mRecipeList.setLayoutManager(new LinearLayoutManager(this));

		mSearchBox.addTextChangedListener(mSearchBoxTextWatcher);
	}

	private void getRecipes(final String url)
	{
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
		                                                  new Response.Listener<JSONObject>()
		                                                  {
			                                                  @Override
			                                                  public void onResponse(
					                                                  JSONObject response)
			                                                  {
				                                                  try
				                                                  {
					                                                  JSONArray recipes = response
							                                                  .getJSONArray(
									                                                  "results");
					                                                  populateRecipeList(recipes);
				                                                  }
				                                                  catch (JSONException e)
				                                                  {
					                                                  e.printStackTrace();
				                                                  }
			                                                  }
		                                                  }, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{

			}
		});

		VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue().add(request);
	}

	private void populateRecipeList(JSONArray data)
	{
		if (data != null)
		{
			if (mRecipeListAdapter == null)
			{
				mRecipeListAdapter = new RecipeListAdapter(data, this);
				mRecipeList.setAdapter(mRecipeListAdapter);
			}
			else
			{
				mRecipeListAdapter.setData(data);
				mRecipeListAdapter.notifyDataSetChanged();
			}
		}
	}
}
