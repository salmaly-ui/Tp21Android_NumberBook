package com.example.phonebookpro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnImporter, btnEnvoyer, btnChercher;
    private EditText champRecherche;
    private RecyclerView listContacts;
    private AdaptateurPersonne adaptateur;
    private final List<Personne> mesPersonnes = new ArrayList<>();
    private PersonneApi api;

    private int nbEnvoyes = 0;
    private int nbEchecs  = 0;

    private TextView txtTotal, txtEnvoyes, txtEchecs;

    // ─── méthode utilitaire Snackbar ───────────────────────────────
    private void snack(String msg) {
        View root = findViewById(android.R.id.content);
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnImporter    = findViewById(R.id.btnImporter);
        btnEnvoyer     = findViewById(R.id.btnEnvoyer);
        btnChercher    = findViewById(R.id.btnChercher);
        champRecherche = findViewById(R.id.champRecherche);
        listContacts   = findViewById(R.id.listContacts);
        txtTotal       = findViewById(R.id.txtTotal);
        txtEnvoyes     = findViewById(R.id.txtEnvoyes);
        txtEchecs      = findViewById(R.id.txtEchecs);

        listContacts.setLayoutManager(new LinearLayoutManager(this));
        adaptateur = new AdaptateurPersonne(mesPersonnes);
        listContacts.setAdapter(adaptateur);

        api = ClientRetrofit.getInstance().create(PersonneApi.class);

        btnImporter.setOnClickListener(v -> verifierPermissionEtImporter());
        btnEnvoyer.setOnClickListener(v  -> envoyerVersServeur());
        btnChercher.setOnClickListener(v -> lancerRecherche());
    }

    // ─── Permission ───────────────────────────────────────────────
    private void verifierPermissionEtImporter() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            importerDepuisTelephone();
        } else {
            demandePermission.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    private final androidx.activity.result.ActivityResultLauncher<String>
            demandePermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            accorde -> {
                if (accorde) {
                    importerDepuisTelephone();
                } else {
                    snack("Permission refusee — impossible de lire les contacts");
                }
            });

    // ─── Import contacts téléphone ────────────────────────────────
    private void importerDepuisTelephone() {
        mesPersonnes.clear();

        Cursor curseur = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        if (curseur != null) {
            while (curseur.moveToNext()) {
                String nom = curseur.getString(curseur.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String tel = curseur.getString(curseur.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                mesPersonnes.add(new Personne(nom, tel));
            }
            curseur.close();
        }

        adaptateur.mettreAJour(mesPersonnes);

        if (txtTotal != null) {
            txtTotal.setText(String.valueOf(mesPersonnes.size()));
        }

        snack(mesPersonnes.size() + " contacts importes");
    }

    // ─── Envoi séquentiel vers le serveur ─────────────────────────
    private void envoyerVersServeur() {
        if (mesPersonnes.isEmpty()) {
            snack("Importez d'abord les contacts");
            return;
        }
        nbEnvoyes = 0;
        nbEchecs  = 0;

        if (txtEnvoyes != null) txtEnvoyes.setText("0");
        if (txtEchecs  != null) txtEchecs.setText("0");

        snack("Envoi en cours pour " + mesPersonnes.size() + " contacts...");
        envoyerContactSuivant(0);
    }

    private void envoyerContactSuivant(int index) {
        if (index >= mesPersonnes.size()) {
            runOnUiThread(() ->
                    snack("Termine : " + nbEnvoyes + " envoyes, " + nbEchecs + " echecs")
            );
            return;
        }

        Personne p = mesPersonnes.get(index);

        api.envoyerPersonne(p).enqueue(new Callback<ReponseApi>() {
            @Override
            public void onResponse(@NonNull Call<ReponseApi> c,
                                   @NonNull Response<ReponseApi> r) {
                if (r.isSuccessful() && r.body() != null && r.body().isReussi()) {
                    nbEnvoyes++;
                } else {
                    nbEchecs++;
                }
                runOnUiThread(() -> {
                    if (txtEnvoyes != null) txtEnvoyes.setText(String.valueOf(nbEnvoyes));
                    if (txtEchecs  != null) txtEchecs.setText(String.valueOf(nbEchecs));
                });
                envoyerContactSuivant(index + 1);
            }

            @Override
            public void onFailure(@NonNull Call<ReponseApi> c,
                                  @NonNull Throwable t) {
                nbEchecs++;
                runOnUiThread(() -> {
                    if (txtEchecs != null) txtEchecs.setText(String.valueOf(nbEchecs));
                    snack("Erreur reseau : " + t.getMessage());
                });
                envoyerContactSuivant(index + 1);
            }
        });
    }

    // ─── Recherche distante ───────────────────────────────────────
    private void lancerRecherche() {
        String terme = champRecherche.getText().toString().trim();

        if (terme.isEmpty()) {
            snack("Veuillez saisir un terme de recherche");
            return;
        }

        api.rechercherPersonne(terme).enqueue(new Callback<List<Personne>>() {
            @Override
            public void onResponse(@NonNull Call<List<Personne>> c,
                                   @NonNull Response<List<Personne>> r) {
                if (r.isSuccessful() && r.body() != null) {
                    adaptateur.mettreAJour(r.body());
                    snack(r.body().size() + " resultat(s) trouve(s)");
                } else {
                    snack("Reponse invalide du serveur");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Personne>> c,
                                  @NonNull Throwable t) {
                snack("Recherche echouee : " + t.getMessage());
            }
        });
    }
}