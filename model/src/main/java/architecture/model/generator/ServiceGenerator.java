package architecture.model.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建时间: 2017/06/19 19:10 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public class ServiceGenerator {

  private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
  private static Retrofit retrofit;

  static {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .enableComplexMapKeySerialization()
        .serializeNulls()
        .setPrettyPrinting()
        .create();

    retrofit = new Retrofit.Builder().baseUrl("BASE_URL")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(ServiceGenerator.httpClientBuilder.build())
        .build();
  }

  public static <S> S createService(Class<S> serviceClass) {
    return retrofit.create(serviceClass);
  }
}
