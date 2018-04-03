package model

type User struct {
	Mail       string
	LoginName  string
	Password   string
	RegistDate int
}

/**
 * check if password is right
 * @param  {[type]} u [description]
 * @return {[type]}   [description]
 */
func (u User) Check(pwd string) (issame bool) {
	issame = (pwd == u.Password)
	return
}
