//GIT URL for pipeline job
File file = new File("./repos.txt")
def repos = file.text.split()

folder("pipelineJobs") {
  authorization {
    permission()
  }
}

repos.each {
  def jobName = it.split("/")[4].replaceAll(".git", "")
  pipelineJob("pipelineJobs/${jobName}"){
    definition {
      cpsScm {
        scm {
          git(it, null)
        }
        scriptPath('Jenkinsfile')
      }
    }
  }
}

//all jobs in one folder
//folder has auth via LDAP
//folder has shared library (between all jobs)
//add README.md
