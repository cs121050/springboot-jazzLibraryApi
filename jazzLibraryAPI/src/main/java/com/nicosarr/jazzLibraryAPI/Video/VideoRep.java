package com.nicosarr.jazzLibraryAPI.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicosarr.jazzLibraryAPI.Artist.Artist;
import com.nicosarr.jazzLibraryAPI.VideoContainsArtist.VideoContainsArtist;
import com.nicosarr.jazzLibraryAPI.Instrument.Instrument;
import com.nicosarr.jazzLibraryAPI.Quote.Quote;
import com.nicosarr.jazzLibraryAPI.Quote.QuoteRowMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;



@Repository
public class VideoRep {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveAll() {
        String jpql = "SELECT v FROM Video v"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }
    
    public String[] retrieveAllVideoNames() {
        String jpql = "SELECT v.video_name FROM Video v";  
        Query query = entityManager.createQuery(jpql);     
        List<String> videoList = query.getResultList();  
        
        return videoList.toArray(new String[0]);
    }    
    
    public int create(Video video) { 
    	String sql = "INSERT INTO Video (duration_id, video_name, video_duration, video_path, type_id, location_id, video_availability) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	return jdbcTemplate.update(sql, video.getDuration_id(), video.getVideo_name(), video.getVideo_duration(),
    			            video.getVideo_path(), video.getType_id(), video.getLocation_id(), video.getVideo_availability());
    } 
     
    public int update(Video video) { 
    	String sql = "UPDATE Video SET duration_id = ?, video_name = ?, video_duration = ?, video_path = ?,"
    		 + "  type_id = ?, location_id = ?, video_availability = ? WHERE video_id = ?";
    	return jdbcTemplate.update(sql, video.getDuration_id(), video.getVideo_name(), video.getVideo_duration(),
	                        video.getVideo_path(), video.getType_id(), video.getLocation_id(), video.getVideo_availability(), video.getVideo_id ());	
    }
      
    public Video retrieveVideoById(int videoId) {
        String jpql = "SELECT v FROM Video v WHERE v.video_id= :videoId"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("videoId", videoId);
        Video video = (Video) query.getSingleResult();
        
        return video;
    }       


    
    
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByArtistId(int artistId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " WHERE vca.artist.artist_id= :artistId"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("artistId", artistId);
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }      
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByInstrumentId(int instrumentId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " INNER JOIN Artist a ON vca.artist.artist_id = a.artist_id "
        		+ " WHERE a.instrument_id= :instrumentId "
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("instrumentId", instrumentId);
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }    
    
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByTypeId(int typeId) {
        String jpql = "SELECT v FROM Video v  WHERE v.type_id = :typeId"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("typeId", typeId);        
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }
    

    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByDurationId(int durationId) {
        String jpql = "SELECT v FROM Video v  WHERE v.duration_id = :durationId"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("durationId", durationId);        
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByInstrumentIdAndTypeId(int instrumentId, int typeId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " INNER JOIN Artist a ON vca.artist.artist_id = a.artist_id "
        		+ " WHERE a.instrument_id= :instrumentId And v.type_id= :typeId"
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("instrumentId", instrumentId);
        query.setParameter("typeId", typeId);        
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }    
    
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByInstrumentIdAndDurationId(int instrumentId, int durationId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " INNER JOIN Artist a ON vca.artist.artist_id = a.artist_id "
        		+ " WHERE a.instrument_id= :instrumentId And v.duration_id= :durationId"
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("instrumentId", instrumentId);
        query.setParameter("durationId", durationId);        
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }    

    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByTypeIdAndDurationId(int typeId, int durationId) {
        String jpql = "SELECT v FROM Video v  WHERE v.type_id = :typeId AND v.duration_id = :durationId"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("typeId", typeId);      
        query.setParameter("durationId", durationId);            
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }  
    

    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByInstrumentIdAndTypeIdAndDurationId(int instrumentId, int typeId, int durationId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " INNER JOIN Artist a ON vca.artist.artist_id = a.artist_id "
        		+ " WHERE a.instrument_id= :instrumentId AND v.type_id= :typeId And v.duration_id= :durationId"
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("instrumentId", instrumentId);
        query.setParameter("typeId", typeId);             
        query.setParameter("durationId", durationId);        
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }  
    

    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByArtistIdAndDurationId(int artistId, int durationId) {
        String jpql = "SELECT v FROM Video v  "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " WHERE vca.artist.artist_id = :artistId AND v.duration_id = :durationId "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("artistId", artistId);      
        query.setParameter("durationId", durationId);            
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }  
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByArtistIdAndTypeId(int artistId, int typeId) {
        String jpql = "SELECT v FROM Video v  "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " WHERE vca.artist.artist_id = :artistId AND v.type_id = :typeId "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("artistId", artistId);      
        query.setParameter("typeId", typeId);            
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }   
    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByArtistIdAndTypeIdAndDurationId(int artistId, int typeId, int durationId) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " WHERE vca.artist_id= :artistId AND v.type_id= :typeId And v.duration_id= :durationId"
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("artistId", artistId);
        query.setParameter("typeId", typeId);             
        query.setParameter("durationId", durationId);        
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }  

    
    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByArtistNameAndArtistSurname(String artistName, String artistSurname) {
        String jpql = "SELECT v FROM Video v "
        		+ " INNER JOIN VideoContainsArtist vca ON vca.video.video_id = v.video_id "
        		+ " INNER JOIN Artist a ON vca.artist.artist_id = a.artist_id "
        		+ " WHERE a.artist_name= :artistName AND a.artist_surname = :artistSurname"
        		+ " order by v.video_id "; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("artistName", artistName);
        query.setParameter("artistSurname", artistSurname);             
  
        List<Video> videoList = query.getResultList();

        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();

	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
               
        return videoList;
    }  
    

    @Transactional  // Initialize the lazy hibernate collection
    public List<Video> retrieveVideoByVideoPath(String videoPath) {
        String jpql = "SELECT v FROM Video v  WHERE v.video_path = :videoPath"; 
        Query query = entityManager.createQuery(jpql, Video.class);
        query.setParameter("videoPath", videoPath);        
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
	      // Initialize each VideoContainsArtist and its artist within the same session
	      for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
	      	artistList.add( vca.getArtist() );
	 
	      video.setArtistList(artistList);  // Set the videos for the artist
	    }
        
        return videoList; 
    }
    
    
	@Transactional 
    public List<Video> retriveRandomVideos(int howManyIds) {
    	
		String sExtraWhere = produseRandVideoIdExtraWhere(howManyIds);

        String jpql = "SELECT v FROM Video v  WHERE 1=0 "+ sExtraWhere; 
        
        Query query = entityManager.createQuery(jpql, Video.class);  
        List<Video> videoList = query.getResultList();  
        
        for (Video video : videoList) {
	      List<Artist> artistList = new ArrayList<>();
	
        // Initialize each VideoContainsArtist and its artist within the same session
        for (VideoContainsArtist vca : video.getVideoContainsArtists()) 
        	artistList.add( vca.getArtist() );
 
        video.setArtistList(artistList);  // Set the videos for the artist

        }
        
    return videoList;	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String produseRandVideoIdExtraWhere(int howManyIds) {
		
    	int countOfVideos =  retriveVideoCount();
    	int randomVideoId[] = randomIdGenerator(countOfVideos, howManyIds);    	
    	
    	StringBuilder sExtraWhere = new StringBuilder("");
    	
    	for(int i = 0 ; i<randomVideoId.length; i ++)
    		sExtraWhere.append("  OR v.video_id = "+ randomVideoId[i]);
    	
    	
    	return sExtraWhere.toString();
		
	}

    

    
    public int retriveVideoCount() { 
    	
        String jpql = "SELECT t.count FROM TableRowCount t WHERE t.table_id = 1"; // table_id=1 : videos
        Query query = entityManager.createQuery(jpql);
        Integer result = (Integer) query.getSingleResult();
        
        return result != null ? result : 0; 
    }

    public static int[] randomIdGenerator(int maxID, int countID){

    	int[] randomIDArray = new int[countID];
    	
    	for (int i=0 ; i<countID ; i++) {	
	        Random r = new Random();
	        int low = 1;
	        int high = maxID;
	        int randomID = r.nextInt(high-low) + low;
	        
	        randomIDArray[i] = randomID;
    	}

        return randomIDArray;
    }

}

