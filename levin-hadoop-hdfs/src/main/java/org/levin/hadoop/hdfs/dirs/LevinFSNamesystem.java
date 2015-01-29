package org.levin.hadoop.hdfs.dirs;

import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.fs.permission.PermissionStatus;

public class LevinFSNamesystem {
    private String supergroup;
    private String fsOwner;
    
    public LevinFSNamesystem(String fsOwner, String supergroup) {
        this.fsOwner = fsOwner;
        this.supergroup = supergroup;
    }
    
    public PermissionStatus createFsOwnerPermissions(FsPermission perm) {
        return new PermissionStatus(fsOwner, supergroup, perm);
    }
}
