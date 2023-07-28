import contests.model.CategorieVarsta;
import contests.model.NumeProba;
import contests.model.Proba;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class RestClient {
    public static final String URL = "http://localhost:8080/proba";

    private RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        int id = 14;
        NumeProba numeProba = new NumeProba(4, "REST");
        CategorieVarsta categorieVarsta = new CategorieVarsta(2, 10, 12);
        Proba proba = new Proba(id, numeProba, categorieVarsta);
        try {
            RestClient restClient = new RestClient();
//            restClient.create(proba);
            System.out.println("Created");
            Proba[] probe = restClient.getAll();
            for (Proba p : probe) {
                System.out.println(p);
            }
            restClient.update(id,new Proba(id, numeProba, categorieVarsta));
            System.out.println("Updated");
//            restClient.delete(id);
            System.out.println("Deleted");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Proba[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Proba[].class));
    }

    public Proba getOne(Integer id) {
        return execute(() -> restTemplate.getForObject(URL + "/" + id, Proba.class));
    }

    public Proba create(Proba proba) {
        return execute(() -> restTemplate.postForObject(URL, proba, Proba.class));
    }

    public void update(Integer id, Proba proba) {
        execute(() -> {
            restTemplate.put(URL + "/" + id, proba);
            return null;
        });
    }

    public void delete(Integer id) {
        execute(() -> {
            restTemplate.delete(URL + "/" + id);
            return null;
        });
    }


}
