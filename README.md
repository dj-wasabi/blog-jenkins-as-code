# Jenkins as Code

This repository contains example code that is discussed on my personal homepage.


## Docker compose

Before we start, make sure that we set an environment variable named `EXPORTED_PASSWORD` which contains the contents of the SSH private key. In my case, my private key that has access to Github is `~/.ssh/wd_id_rsa`. So my command looks like:

```sh
export EXPORTED_PASSWORD=$(cat ~/.ssh/wd_id_rsa)
```

Once done, execute the `docker compose` command:

```sh
cd server
docker compose up -d
```

Overview of environment variables

| Environment | Description |
| ------------|-------------|
|`JENKINS_ADMIN_NAME`| The name of the admin user. Default: `Administrator`|
|`JENKINS_ADMIN_USERNAME`| The username of the admin user which you use to login. Default: `admin`|
|`JENKINS_ADMIN_PASSWORD`| The password for the username mentioned in `JENKINS_ADMIN_USERNAME`|
|`JENKINS_JOB_DSL_URL`| The git url where the groovy files can be found with the jobs configuration.|
|`JENKINS_JOB_DSL_PATH`| The name of directory where the jobs can be found. Default: `jobs`|
