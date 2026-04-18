package  com.example.phonebookpro ;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PersonneApi {

    @POST("ajouterPersonne.php")
    Call<ReponseApi> envoyerPersonne(@Body Personne p);

    @GET("listerPersonnes.php")
    Call<List<Personne>> recupererTout();

    @GET("rechercherPersonne.php")
    Call<List<Personne>> rechercherPersonne(@Query("terme") String terme);
}
