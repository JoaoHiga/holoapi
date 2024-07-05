package com.takomuraragi.holoapi.holoapi.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takomuraragi.holoapi.holoapi.models.ChannelInformation;
import com.takomuraragi.holoapi.holoapi.models.Vtuber;
import com.takomuraragi.holoapi.holoapi.services.ApiConsult;
import com.takomuraragi.holoapi.holoapi.services.UrlBuilder;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public void execute (String BASE_URL, String API_KEY) throws JsonProcessingException, URISyntaxException {
        ApiConsult apiConsult = new ApiConsult();
        ObjectMapper jsonConverter = new ObjectMapper();
        Scanner input = new Scanner(System.in);

        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("type", "vtuber");
        queryParameters1.put("org", "Hololive");
        queryParameters1.put("offset", "0");
        queryParameters1.put("limit", "100");
        String initialUrl = new UrlBuilder().buildURL(BASE_URL, queryParameters1);

        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("type", "vtuber");
        queryParameters2.put("org", "Hololive");
        queryParameters2.put("offset", "100");
        queryParameters2.put("limit", "100");
        String secondUrl = new UrlBuilder().buildURL(BASE_URL, queryParameters2);

        System.out.println("""
                ***************************************************************
                                     Bienvenido a HoloAPI
                ***************************************************************
                """);
        System.out.println("Obteniendo información de canales....");

        String json = apiConsult.obtainData(initialUrl, API_KEY);
        List<ChannelInformation> channels1 = jsonConverter.readValue(json, new TypeReference<>() {});
        List<Vtuber> vtubers1 = channels1.stream()
                .map(Vtuber::new)
                .collect(Collectors.toList());

        json = apiConsult.obtainData(secondUrl, API_KEY);
        List<ChannelInformation> channels2 = jsonConverter.readValue(json, new TypeReference<>() {});
        List<Vtuber> vtubers2 = channels2.stream()
                .map(Vtuber::new)
                .collect(Collectors.toList());

        List<Vtuber> vtubers = new ArrayList<>(vtubers1);
        vtubers.addAll(vtubers2);
        System.out.println(vtubers.size());

        boolean executing = true;
        while (executing) {
            displayMenu();
            try {
                int selectedOption = Integer.valueOf(input.nextLine());
                switch (selectedOption) {
                    case 1:
                        searchByName(vtubers, input);
                        break;
                    case 2:
                        searchByGroup(vtubers,input);
                        break;
                    case 3:
                        topFiveSubCount(vtubers);
                        break;
                    case 4:
                        topFiveVideoCount(vtubers);
                        break;
                    case 5:
                        showStats(vtubers);
                        break;
                    case 6:
                        executing = false;
                        break;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Ingrese una opción válida.");
            }
        }
    }

    public void displayMenu() {
        System.out.println("""
                ***************************************************************
                Seleccione una opción:
                    1. Consultar por nombre de Vtuber.
                    2. Consultar por grupo o generación.
                    3. Consultar top 5 vtubers por numero de suscriptores.
                    4. Consultar top 5 vtuber por numero de videos.
                    5. Consultar estadísticas generales.
                    6. Salir.
                ***************************************************************
                """);
    }

    public void searchByName(List<Vtuber> vtubers, Scanner input) {
        System.out.println("Ingrese el nombre de la vtuber que desee buscar:");
        String introducedName = input.nextLine().toLowerCase();
        Optional<Vtuber> channel = vtubers.stream()
                .filter(c -> c.getVtuberName() != null)
                .filter(c -> c.getVtuberName().toLowerCase().contains(introducedName))
                .findFirst();
        if (channel.isPresent()) {
            System.out.println(channel.get());
        } else {
            System.out.println("Vtuber no hallada.");
        }
    }

    public void searchByGroup(List<Vtuber> vtubers, Scanner scanner) {
        System.out.println("Seleccione el grupo que desea ver");
        try {
            //Obtiene los nombres de los grupos en una lista sin duplicados
            List<String> groupList = vtubers.stream()
                    .sorted(Comparator.comparing(Vtuber::getGroup))
                    .map(Vtuber::getGroup)
                    .distinct()
                    .toList();

            //Muestra los nombres de grupos con un número que indican su opción
            groupList.forEach(v -> System.out.println(groupList.indexOf(v) + 1 + ". " + v));

            //Toma la elección del usuario y lo traduce a un nombre del grupo de la lista
            int selectedGroupNumber = Integer.valueOf(scanner.nextLine());
            String selectedGroup = groupList.get(selectedGroupNumber - 1);
            System.out.println("Mostrando vtubers del grupo: " + selectedGroup);

            //Filtra la lista de canales de vtubers con el grupo seleccionado
            List<Vtuber> vtuberFilteredByGroup = vtubers.stream().
                    filter(v -> v.getGroup().contains(selectedGroup))
                    .toList();

            //Muestra los nombres de los canales pertenecientes al grupo con un número que indica su opción
            vtuberFilteredByGroup.forEach(v ->
                    System.out.println(vtuberFilteredByGroup.indexOf(v) + 1 + ". " + v.getVtuberName()));

            //Selecciona el canal de la lista de canales del grupo
            int selectedVtuberNumber = Integer.valueOf(scanner.nextLine());
            Vtuber selectedVtuber = vtuberFilteredByGroup.get(selectedVtuberNumber - 1);

            //Imprime la información del canal seleccionado
            System.out.println(selectedVtuber.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Opción inválida.");
        }
    }

    public void topFiveSubCount(List<Vtuber> vtubers) {
        List<Vtuber> topFiveSubCountVtuber = vtubers.stream()
                .sorted(Comparator.comparing(Vtuber::getSubscriberCount).reversed())
                .limit(5)
                .toList();

        System.out.println("""
                ***************************************************************
                               Top 5 Vtubers by Subscriber count
                ***************************************************************
                """);
        topFiveSubCountVtuber.forEach(v -> System.out.println(
                topFiveSubCountVtuber.indexOf(v) + 1 + ". " + v.getVtuberName() + " : " + v.getSubscriberCount()
        ));
    }

    public void topFiveVideoCount(List<Vtuber> vtubers) {
        List<Vtuber> topFiveVideoCountVtuber = vtubers.stream()
                .sorted(Comparator.comparing(Vtuber::getVideoCount).reversed())
                .limit(5)
                .toList();

        System.out.println("""
                ***************************************************************
                                 Top 5 Vtubers by Video count
                ***************************************************************
                """);
        topFiveVideoCountVtuber.forEach(v -> System.out.println(
                topFiveVideoCountVtuber.indexOf(v) + 1 + ". " + v.getVtuberName() + " : " + v.getVideoCount()
        ));
    }

    public void showStats(List<Vtuber> vtubers) {
        IntSummaryStatistics videoEst = vtubers.stream()
                .filter(v -> v.getVideoCount() > 0)
                .collect(Collectors.summarizingInt(Vtuber::getVideoCount));

        IntSummaryStatistics subsEst = vtubers.stream()
                .filter(v -> v.getSubscriberCount() > 0)
                .collect(Collectors.summarizingInt(Vtuber::getSubscriberCount));

        System.out.println("Promedio de cantidades de video: " + Math.round(videoEst.getAverage()));
        System.out.println("Mayor cantidad de videos: " + videoEst.getMax());
        System.out.println("Menor cantidad de videos: " + videoEst.getMin());
        System.out.println("Promedio de suscriptores: " + Math.round(subsEst.getAverage()));
        System.out.println("Mayor cantidad de suscriptores: " + subsEst.getMax());
        System.out.println("Menor cantidad de suscriptores: " + subsEst.getMin());
    }
}
