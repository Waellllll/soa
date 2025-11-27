package metiers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import entities.UniteEnseignement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/UE")
public class UniteEnseignementResource {

    // Use 'code' as the unique key instead of a separate id
    private static Map<Integer, UniteEnseignement> ueDB = new HashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUE(UniteEnseignement ue) {
        // Store by code
        ueDB.put(ue.getCode(), ue);
        return Response.status(Response.Status.CREATED).entity(ue).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEs(@QueryParam("semestre") Integer semestre,
                           @QueryParam("code") Integer code) {
        Collection<UniteEnseignement> values = ueDB.values();

        if (semestre != null) {
            values = values.stream()
                    .filter(u -> u.getSemestre() == semestre)
                    .toList();
        }
        if (code != null) {
            values = values.stream()
                    .filter(u -> u.getCode() == code)
                    .toList();
        }
        return Response.ok(values).build();
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        if (!ueDB.containsKey(code)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Ensure the code stays consistent
        ue.setCode(code);
        ueDB.put(code, ue);
        return Response.ok(ue).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        if (ueDB.remove(code) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
