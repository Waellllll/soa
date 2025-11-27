package metiers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import entities.Module;

import java.util.*;

@Path("/modules")
public class ModuleResource {

    private static Map<String, Module> moduleDB = new HashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createModule(Module module) {
        moduleDB.put(module.getMatricule(), module);
        return Response.status(Response.Status.CREATED).entity(module).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModules() {
        return Response.ok(moduleDB.values()).build();
    }

    @GET
    @Path("/{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModule(@PathParam("matricule") String matricule) {
        Module module = moduleDB.get(matricule);
        if (module == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(module).build();
    }

    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (!moduleDB.containsKey(matricule)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        module.setMatricule(matricule);
        moduleDB.put(matricule, module);
        return Response.ok(module).build();
    }

    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (moduleDB.remove(matricule) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/UE")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@QueryParam("codeUE") int codeUE) {
        List<Module> modules = moduleDB.values().stream()
                .filter(m -> m.getUniteEnseignement() != null &&
                        m.getUniteEnseignement().getCode() == codeUE)
                .toList();
        return Response.ok(modules).build();
    }


}
