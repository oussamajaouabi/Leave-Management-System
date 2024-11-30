package services;

import java.util.Date;
import java.util.List;

import models.Leave;
import models.LeaveType;

public interface LeaveInterface {
    List<Leave> showLeavesListByEmployee(int employeId);
    
    List<Leave> showLeavesInProgressByEmployee();
    
    List<Leave> searchLeave(int employeId, String nomConge);

    void createLeave(int employeId, String nomConge, String description, 
                    Date dateDebut, Date dateFin, LeaveType typeConge) throws Exception;

    void updateLeave(int congeId, String nomConge, String description, Date dateDebut, 
                       Date dateFin, LeaveType typeConge) throws Exception;

    void deleteLeave(int congeId);
	
    void validateLeave(int congeId);
    
    void refuseLeave(int congeId);
}
